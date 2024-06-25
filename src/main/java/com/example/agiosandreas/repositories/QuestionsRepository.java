package com.example.agiosandreas.repositories;

import com.example.agiosandreas.users.MultipleChoiceQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<MultipleChoiceQuestions, Long> {

    @Query("SELECT DISTINCT q FROM MultipleChoiceQuestions q WHERE q.moduleId = 1 ")
    List<MultipleChoiceQuestions> getQuestionsForPart1();

    @Query("SELECT DISTINCT q FROM MultipleChoiceQuestions q WHERE q.moduleId = 2 ")
    List<MultipleChoiceQuestions> getQuestionsForPart2();


}

