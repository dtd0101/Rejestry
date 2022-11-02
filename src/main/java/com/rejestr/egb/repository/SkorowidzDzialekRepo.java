package com.rejestr.egb.repository;

import com.rejestr.egb.entity.SkorowidzDzialek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkorowidzDzialekRepo extends JpaRepository<SkorowidzDzialek, Long> {

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.GMINA  LIKE :gmina")
    List<SkorowidzDzialek> findByGmina(@Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc")
    List<SkorowidzDzialek> findByMiejscowosc(@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
            "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt")
    List<SkorowidzDzialek> findByTeryt(@Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzDzialek> findByGminaAndMiejscowosc(@Param("gmina") String gmina,@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzDzialek> findByGminaAndTeryt(@Param("gmina") String gmina, @Param("teryt")  String teryt);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc ")
    List<SkorowidzDzialek> findByMiejscowoscAndTeryt(@Param("miejscowosc") String miejscowosc,@Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina")
    List<SkorowidzDzialek> findByGminaAndMiejscowoscAndTeryt(@Param("gmina") String gmina,@Param("miejscowosc") String miejscowosc, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s WHERE s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByRok(@Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByGminaAndRok(@Param("gmina") String gmina, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByMiejscowoscAndRok(@Param("miejscowosc") String miejscowosc, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.TERYT_NUMBER  LIKE :teryt" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByTerytAndRok(@Param("teryt") String teryt, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByGminaAndMiejscowoscAndRok(@Param("gmina") String gmina,@Param("miejscowosc") String miejscowosc, @Param("rok") String rok);

    @Query(nativeQuery = true, value =
            "Select * FROM SKOROWIDZ s " +
                    "INNER JOIN TERYT t on s.TERYT_ID = t.ID " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " AND t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " AND t.TERYT_NUMBER  LIKE :teryt" +
                    " AND s.ROK  LIKE :rok")
    List<SkorowidzDzialek> findByGminaAndMiejscowoscAndTerytAndRok(@Param("gmina") String gmina,@Param("miejscowosc") String miejscowosc,@Param("teryt") String teryt, @Param("rok") String rok);


}
