package com.example.agiosandreas.repositories;

import com.example.agiosandreas.model.Answers;
import com.example.agiosandreas.model.MultipleChoiceQuestions;
import com.example.agiosandreas.model.TheoryPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswersRepository extends JpaRepository<Answers,Long> {

    @Query("SELECT DISTINCT a FROM Answers a WHERE a.userID = :userId")
    List<Answers> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c.correct_answer FROM MultipleChoiceQuestions c WHERE c.id = :questionId")
    String getCorrectAnswer(@Param("questionId") Long questionId);

    @Query("SELECT c FROM MultipleChoiceQuestions c WHERE c.id = :questionId")
    MultipleChoiceQuestions getQuestionById(@Param("questionId") Long questionId);

    @Query("SELECT p.partOfModule FROM MultipleChoiceQuestions p WHERE p.id = :questionId")
    Long getPartOfModule(@Param("questionId") Long questionId);

    @Query("SELECT m.moduleId FROM MultipleChoiceQuestions m WHERE m.id = :questionId")
    Long getModuleId(@Param("questionId") Long questionId);

    @Query("SELECT r FROM TheoryPart r WHERE r.partOfModule = :partOfModule AND r.moduleId = :moduleId")
    TheoryPart getRepetTheory(@Param("partOfModule") Long partOfModule, @Param("moduleId") Long moduleId);

    @Query("SELECT q FROM MultipleChoiceQuestions q WHERE q.partOfModule = :partOfModule AND q.moduleId = :moduleId")
    List<MultipleChoiceQuestions> findQuestionsByPartId(@Param("partOfModule") Long partOfModule, @Param("moduleId") Long moduleId);
}
