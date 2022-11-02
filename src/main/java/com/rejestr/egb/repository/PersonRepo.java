package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query(nativeQuery = true, value =
            "Select * " +
                    " FROM person p " +
                    "WHERE p.imie  LIKE %:imie% AND  p.nazwisko  LIKE %:nazwisko% and p.imie_ojca  LIKE %:imieOjca% and p.imie_matki  LIKE %:imieMatki% " )
    List<Person> findPerson(@Param("imie") String imie, @Param("nazwisko") String nazwisko, @Param("imieOjca") String  imieOjca,  @Param("imieMatki") String  imieMatki);
}
