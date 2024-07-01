package com.example.agiosandreas.repositories;

import com.example.agiosandreas.users.TheoryPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheoryPartRepository extends JpaRepository<TheoryPart, Long> {

    @Query("SELECT DISTINCT t FROM TheoryPart t WHERE t.moduleId = 1 ")
    List<TheoryPart> getModule1Parts();

    @Query("SELECT DISTINCT t FROM TheoryPart t WHERE t.moduleId = 2 ")
    List<TheoryPart> getModule2Parts();

   @Query("SELECT DISTINCT t FROM TheoryPart t WHERE t.moduleId = 3 ")
   List<TheoryPart> getModule3Parts();
}
