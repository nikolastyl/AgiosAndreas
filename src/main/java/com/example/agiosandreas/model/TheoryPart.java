package com.example.agiosandreas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "theory_parts")
public class TheoryPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "part_of_module")
    private Long partOfModule;

    @Column(name = "title_of_part")
    private String titleOfPart;

    @Column(name = "content")
    private String content;

    public TheoryPart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getPartOfModule() {
        return partOfModule;
    }

    public void setPartOfModule(Long partOfModule) {
        this.partOfModule = partOfModule;
    }

    public String getTitleOfPart() {
        return titleOfPart;
    }

    public void setTitleOfPart(String titleOfPart) {
        this.titleOfPart = titleOfPart;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

