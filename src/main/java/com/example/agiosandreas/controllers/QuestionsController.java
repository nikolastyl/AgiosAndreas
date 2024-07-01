package com.example.agiosandreas.controllers;

import com.example.agiosandreas.repositories.QuestionsRepository;
import com.example.agiosandreas.users.MultipleChoiceQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping("/for-module-1")
    public List<MultipleChoiceQuestions> getQuestions1() {return questionsRepository.getQuestionsForPart1();
    }

    @GetMapping("/for-module-2")
    public List<MultipleChoiceQuestions> getQuestions2() {
        return questionsRepository.getQuestionsForPart2();
    }

    @GetMapping("/for-module-3")
    public List<MultipleChoiceQuestions> getQuestions3() {return questionsRepository.getQuestionsForPart3();}

}
