package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Teryt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerytRepo extends JpaRepository<Teryt, Long> {


    @Query(nativeQuery = true, value =
            "Select * " +
                    " FROM teryt t " +
                    "WHERE t.gmina  LIKE %:gmina% AND  t.miejscowosc  LIKE %:miejscowosc%  and t.teryt_number  LIKE %:terytNumber% " )
    List<Teryt> findTeryt(@Param("gmina") String gmina, @Param("terytNumber") String terytNumber, @Param("miejscowosc") String  miejscowosc);
}
