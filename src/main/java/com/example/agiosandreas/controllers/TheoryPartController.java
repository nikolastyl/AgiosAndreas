package com.example.agiosandreas.controllers;

import com.example.agiosandreas.repositories.TheoryPartRepository;
import com.example.agiosandreas.model.TheoryPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/theory-parts")
public class TheoryPartController {

    @Autowired
    private TheoryPartRepository theoryPartRepository;

    @GetMapping("/module-1")
    public List<TheoryPart> getTheoryParts1() {
        return theoryPartRepository.getModule1Parts();
    }

    @GetMapping("/module-2")
    public List<TheoryPart> getTheoryParts2() {
        return theoryPartRepository.getModule2Parts();
    }

    @GetMapping("/module-3")
    public List<TheoryPart> getTheoryParts3() {return theoryPartRepository.getModule3Parts();}


}
