package com.rejestr.egb.repository;

import com.rejestr.egb.entity.SkorowidzWlascicieli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkorowidzWlaRepo extends JpaRepository<SkorowidzWlascicieli, Long> {

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.GMINA  LIKE :gmina")
    List<SkorowidzWlascicieli> findByGmina(@Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc")
    List<SkorowidzWlascicieli> findByMiejscowosc(@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt")
    List<SkorowidzWlascicieli> findByTeryt(@Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzWlascicieli> findByGminaAndMiejscowosc(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzWlascicieli> findByGminaAndTeryt(@Param("gmina") String gmina, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc ")
    List<SkorowidzWlascicieli> findByMiejscowoscAndTeryt(@Param("miejscowosc") String miejscowosc, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzWlascicieli> findByGminaAndMiejscowoscAndTeryt(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s WHERE s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByRok(@Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByGminaAndRok(@Param("gmina") String gmina, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByMiejscowoscAndRok(@Param("miejscowosc") String miejscowosc, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByTerytAndRok(@Param("teryt") String teryt, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByGminaAndMiejscowoscAndRok(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select s.* FROM SKOROWIDZ_WLA s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND t.TERYT_NUMBER  LIKE :teryt" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzWlascicieli> findByGminaAndMiejscowoscAndTerytAndRok(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("teryt") String teryt, @Param("rok") String rok);


}
