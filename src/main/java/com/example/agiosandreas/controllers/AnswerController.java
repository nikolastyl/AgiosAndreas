package com.example.agiosandreas.controllers;

import com.example.agiosandreas.repositories.AnswersRepository;
import com.example.agiosandreas.model.Answers;
import com.example.agiosandreas.model.MultipleChoiceQuestions;
import com.example.agiosandreas.model.TheoryPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswersRepository answersRepository;

    @PostMapping
    public void Answers(@RequestBody Answers answers) {
        answersRepository.save(answers);
    }

    @GetMapping("/checking")
    private Map<String, Object> checkUserAnswers(@RequestParam int userId) {
        List<Answers> userAnswers = answersRepository.findByUserId((long) userId);
        Map<Long, Integer> incorrectAnswersCount = new HashMap<>();
        Map<Long, Long> timeSpentPerPart = new HashMap<>();
        Set<TheoryPart> theoryPartsToExerciseSet = new HashSet<>();
        List<MultipleChoiceQuestions> incorrectQuestions = new ArrayList<>();

        for (Answers answer : userAnswers) {
            Long questionId = answer.getQuestionId();
            String userAnswer = answer.getAnswer();
            String correctAnswer = answersRepository.getCorrectAnswer(questionId);
            Long partId = answersRepository.getPartOfModule(questionId);
            Long moduleId = answersRepository.getModuleId(questionId);

            if (!userAnswer.equals(correctAnswer)) {
                incorrectAnswersCount.put(partId, incorrectAnswersCount.getOrDefault(partId, 0) + 1);
                MultipleChoiceQuestions incorrectQuestion = answersRepository.getQuestionById(questionId);
                if (incorrectQuestion != null) {
                    incorrectQuestions.add(incorrectQuestion);
                }
            }

            long timeSpent = Long.parseLong(answer.getTime());
            timeSpentPerPart.put(partId, timeSpentPerPart.getOrDefault(partId, 0L) + timeSpent);

            for (Map.Entry<Long, Integer> entry : incorrectAnswersCount.entrySet()) {
                Long part_Id = entry.getKey();
                int incorrectCount = entry.getValue();
                Long time_Spent = timeSpentPerPart.getOrDefault(part_Id, 0L);

                if (incorrectCount >= 2 || (incorrectCount == 1 && time_Spent > 10000)) {
                    TheoryPart part = answersRepository.getRepetTheory(part_Id, moduleId);
                    if (part != null) {
                        theoryPartsToExerciseSet.add(part);
                    }
                }
            }
        }

        List<TheoryPart> theoryPartsToExercise = new ArrayList<>(theoryPartsToExerciseSet);

        incorrectQuestions = incorrectQuestions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("theoryPartsToExercise", theoryPartsToExercise);

        if (incorrectQuestions.size() < 5) {
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 26; i++) {//αλλαγη στον αριθμο ερωτησεων
                numbers.add(i);
            }

            Collections.shuffle(numbers);
            List<Integer> randomNumbers = numbers.subList(0, 5);

            for (Integer randomNumber : randomNumbers) {
                MultipleChoiceQuestions test = answersRepository.getQuestionById((long) randomNumber);
                if (test != null) {
                    incorrectQuestions.add(test);
                }
            }
        }

        response.put("questionsToExercise", incorrectQuestions);

        System.out.println("Response: " + response);
        System.out.println(response.size());

        return response;
    }

    @GetMapping("/get_score")
    private int getScore(@RequestParam int userId) {
        List<Answers> userAnswers = answersRepository.findByUserId((long) userId);
        int incorrectAnswersCount = 0;

        for (Answers answer : userAnswers) {
            Long questionId = answer.getQuestionId();
            String userAnswer = answer.getAnswer();
            String correctAnswer = answersRepository.getCorrectAnswer(questionId);
            if (!userAnswer.equals(correctAnswer)) {
                incorrectAnswersCount++;
            }
        }

        System.out.println(incorrectAnswersCount);
        return incorrectAnswersCount;
    }
}
