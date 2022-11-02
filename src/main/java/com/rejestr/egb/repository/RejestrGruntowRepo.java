package com.rejestr.egb.repository;

import com.rejestr.egb.entity.RejestrGruntow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RejestrGruntowRepo extends JpaRepository<RejestrGruntow, Long> {


    String SELECT = "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg ";
    String DZIALKA = "(SELECT p.rejestr_gruntow_id from PARCEL as p  where p.PARCEL_NUMBER like :dzialka)";
    String PERSON_IMIE = "(SELECT prg.REJESTR_GRUNTOWS_ID " +
            "from PERSON_REJESTR_GRUNTOWS as prg " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID = p.ID where p.IMIE LIKE :imie%)";

//    String test = String.format(" WHERE p.%s LIKE :imie% " , "imie");

    @Query(value = "SELECT count(id) FROM REJESTR_GRUNTOW;", nativeQuery = true)
    int countRejestry();

    @Query(value = "Select DISTINCT c.* from REJESTR_GRUNTOW c where c.NR_JEDNOSTKI_REJESTROWEJ is not null order by c.id asc limit ?1,  ?2 ", nativeQuery = true)
    List<RejestrGruntow> offsetAndLimit(int offset, int limit);

    @Query(nativeQuery = true, value =
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
                    "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
                    "INNER JOIN teryt t on p.teryt_id = t.id " +
                    " WHERE rg.NR_JEDNOSTKI_REJESTROWEJ " +
            "LIKE :filterText% or rg.DATA_ZALOZENIA = :filterText " +
            "ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByJednostka(@Param("filterText") String filterText);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on d.teryt_id = t.id " +
            " WHERE p.IMIE LIKE :imie% " +
            "ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByWlasciciel(@Param("imie") String imie);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on d.teryt_id = t.id " +
            "WHERE p.nazwisko LIKE :nazwisko " +
            "ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByNazwisko(@Param("nazwisko") String nazwisko);


    @Query(nativeQuery = true, value = "" + SELECT +
            " WHERE rg.id in " + DZIALKA)
    List<RejestrGruntow> searchByDzialka(@Param("dzialka") String dzialka);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on d.teryt_id = t.id " +
            "WHERE p.imie like :wlasciciel AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) "  +
            " ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByJednostkaAndWlasciciel(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on d.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :nazwisko " +
            " ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByJednostkaAndNazwisko(@Param("jednostka") String jednostka, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.PARCEL_NUMBER  LIKE :dzialka" +
            " ORDER BY t.miejscowosc ASC")
    List<RejestrGruntow> searchByJednostkaAndDzialka(@Param("jednostka") String jednostka, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE p.nazwisko  LIKE :nazwisko and p.imie LIKE :wlasciciel  ")
    List<RejestrGruntow> searchByWlascicielAndNazwisko(@Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE d.PARCEL_NUMBER  LIKE :dzialka and p.IMIE LIKE :wlasciciel  ")
    List<RejestrGruntow> searchByWlascicielAndDzialka(@Param("wlasciciel") String wlasciciel , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE d.PARCEL_NUMBER  LIKE :dzialka and p.nazwisko LIKE :nazwisko  ")
    List<RejestrGruntow> searchByNazwiskoAndDzialka(@Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE d.PARCEL_NUMBER  LIKE :dzialka and p.nazwisko LIKE :nazwisko  and p.IMIE LIKE :wlasciciel ")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndDzialka(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwisko(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.nazwisko LIKE :nazwisko and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndNazwiskoAndDzialka(@Param("jednostka") String jednostka, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.TERYT_NUMBER  LIKE :teryt")
    List<RejestrGruntow> searchByJednostkaAndTeryt(@Param("jednostka") String jednostka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.TERYT_NUMBER  LIKE :teryt " +
            "and p.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndTerytAndDzialka(@Param("jednostka") String jednostka, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.GMINA LIKE :gmina")
    List<RejestrGruntow> searchByJednostkaAndGmina(@Param("jednostka") String jednostka, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.GMINA LIKE :gmina AND t.miejscowosc LIKE :miejscowosc")
    List<RejestrGruntow> searchByJednostkaAndGminaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.GMINA LIKE :gmina AND t.miejscowosc LIKE :miejscowosc" +
            " and p.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndGminaAndMiejscowoscAndDzialka(@Param("jednostka") String jednostka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.GMINA LIKE :gmina " +
            " and p.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndGminaAndDzialka(@Param("jednostka") String jednostka, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.GMINA LIKE :gmina AND t.miejscowosc LIKE :miejscowosc")
    List<RejestrGruntow> searchByJednostkaAndGminaAndTeryt(@Param("jednostka") String jednostka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.miejscowosc LIKE :miejscowosc")
    List<RejestrGruntow> searchByJednostkaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL p on rg.ID  = p.REJESTR_GRUNTOW_ID " +
            "INNER JOIN teryt t on p.teryt_id = t.id " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            " and t.miejscowosc LIKE :miejscowosc  and t.TERYT_NUMBER  LIKE :teryt")
    List<RejestrGruntow> searchByJednostkaAndMiejscowoscAndTeryt(@Param("jednostka") String jednostka, @Param("miejscowosc") String miejscowosc, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt")
    List<RejestrGruntow> searchByTeryt(@Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.GMINA LIKE :gmina")
    List<RejestrGruntow> searchByGmina(@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.miejscowosc LIKE :miejscowosc")
    List<RejestrGruntow> searchByMiejscowosc(@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt and t.GMINA LIKE :gmina")
    List<RejestrGruntow> searchByTerytAndGmina(@Param("teryt") String teryt, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt and t.MIEJSCOWOSC LIKE :miejscowosc")
    List<RejestrGruntow> searchByTerytAndMiejscowosc(@Param("teryt") String teryt, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt and t.MIEJSCOWOSC LIKE :miejscowosc and t.GMINA LIKE :gmina")
    List<RejestrGruntow> searchByTerytAndMiejscowoscAndGmina(@Param("teryt") String teryt, @Param("miejscowosc") String miejscowosc, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  (t.TERYT_NUMBER LIKE :teryt )" +
            " AND d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByDzialkaAndTeryt(@Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC LIKE :miejscowosc" +
            " AND d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMiejscowoscAndDzialka(@Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND p.IMIE LIKE :wlasciciel and d.PARCEL_NUMBER LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel,@Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  AND p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  AND p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  ")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndTeryt(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "and p.nazwisko LIKE :nazwisko  " +
            "and t.gmina  LIKE :gmina")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndGmina(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  p.IMIE LIKE :wlasciciel " +
            "and p.nazwisko LIKE :nazwisko  " +
            "and t.miejscowosc  LIKE :miejscowosc")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMiejscowosc(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  p.nazwisko LIKE :nazwisko  " +
            "and t.miejscowosc  LIKE :miejscowosc")
    List<RejestrGruntow> searchByNazwiskoAndMiejscowosc(@Param("nazwisko") String nazwisko, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "and p.nazwisko LIKE :nazwisko  " +
            "and t.gmina  LIKE :gmina " +
            "and t.miejscowosc  LIKE :miejscowosc")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndGminaAndMiejscowosc(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndOjciecAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel And p.NAZWISKO LIKE :nazwisko  AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndOjciecAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("ojciec") String ojciec, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("ojciec") String ojciec, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndOjciecAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndGmina(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndGminaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("matka") String matka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGmina(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowoscAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka " +
            " and d.PARCEL_NUMBER  LIKE :dzialka " +
            "AND t.TERYT_NUMBER  LIKE :teryt ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowoscAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("matka") String matka, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka)" +
            " and p.IMIE LIKE :wlasciciel" +
            " And p.NAZWISKO LIKE :nazwisko  AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("matka") String matka, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("matka") String matka, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndOjciecAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGmina(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka " +
            "AND t.TERYT_NUMBER  LIKE :teryt ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel  AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndOjciecAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka)" +
            " and p.IMIE LIKE :wlasciciel" +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel  AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndMatkaAndOjciecAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel ,@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialka(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndWlascicielAndNazwiskoAndOjciec(@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt" +
            " AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and  p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByJednostkaAndOjciecAndDzialkaAndTeryt(@Param("jednostka") String jednostka,@Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt " +
            " and p.IMIE LIKE :wlasciciel " +
            " AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndOjciecAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel ,@Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt " +
            " and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko " +
            " AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndOjciecAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "  " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "and p.IMIE LIKE :wlasciciel  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(@Param("wlasciciel") String wlasciciel, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "and p.IMIE LIKE :wlasciciel  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByWlascicielAndMatkaAndOjciecAndGminaAndMiejscowosc(@Param("wlasciciel") String wlasciciel, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "  " +
            "and p.IMIE LIKE :wlasciciel and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka " +
            "AND t.TERYT_NUMBER  LIKE :teryt ")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt    and p.IMIE LIKE :wlasciciel  AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndMatkaAndOjciecAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel , @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  " +
            " and p.IMIE LIKE :wlasciciel" +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt(@Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt " +
            "AND  t.gmina  LIKE :gmina  " +
            " and p.IMIE LIKE :wlasciciel" +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndDzialka(@Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " and p.IMIE LIKE :wlasciciel" +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndMiejscowoscAndDzialka(@Param("wlasciciel") String wlasciciel , @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    //WLASCICIEL
    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt " +
            "  and p.nazwisko LIKE :nazwisko " +
            " AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByNazwiskoAndOjciecAndDzialkaAndTeryt( @Param("nazwisko") String nazwisko, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "  " +
            " and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc( @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.miejscowosc  LIKE :miejscowosc  " +
            " and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndMiejscowosc( @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndMiejscowosc(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            " and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndGmina(@Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(@Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " and p.nazwisko LIKE :nazwisko  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka " +
            "AND t.TERYT_NUMBER  LIKE :teryt ")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt( @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  " +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt( @Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt " +
            "AND  t.gmina  LIKE :gmina  " +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndGminaAndDzialka(@Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " And p.NAZWISKO LIKE :nazwisko   AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByNazwiskoAndMatkaAndOjciecAndMiejscowoscAndDzialka(@Param("nazwisko") String nazwisko, @Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    //OJCIEC

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciec(@Param("ojciec") String ojciec);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndDzialka(@Param("ojciec") String ojciec,@Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  p.imie_ojca like :ojciec " +
            " AND   t.TERYT_NUMBER  LIKE :teryt)")
    List<RejestrGruntow> searchByOjciecAndTeryt(@Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt " +
            " AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndDzialkaAndTeryt(@Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndGminaAndMiejscowosc(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka")
    List<RejestrGruntow> searchByMatkaAndGminaAndMiejscowosc(@Param("matka") String matka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByOjciecAndGminaAndMiejscowosc(@Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka")
    List<RejestrGruntow> searchByMatkaAndMiejscowosc(@Param("matka") String matka, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.miejscowosc  LIKE :miejscowosc  " +
            "AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByOjciecAndMiejscowosc(@Param("ojciec") String ojciec, @Param("miejscowosc") String miejscowosc);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndGmina(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  p.imie_ojca like :ojciec  ")
    List<RejestrGruntow> searchByOjciecAndGmina(@Param("ojciec") String ojciec, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            " AND  p.imie_matki like :matka")
    List<RejestrGruntow> searchByMatkaAndGmina(@Param("matka") String matka, @Param("gmina") String gmina);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_ojca like :ojciec  AND p.IMIE LIKE :wlasciciel")
    List<RejestrGruntow> searchByOjciecAndWlsciciel(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_ojca like :ojciec  AND (p.IMIE_OJCA <> '' and p.imie_ojca is not null ) AND p.nazwisko LIKE :nazwisko")
    List<RejestrGruntow> searchByOjciecAndNazwisko(@Param("ojciec") String ojciec, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_ojca like :ojciec  AND p.IMIE LIKE :wlasciciel And p.NAZWISKO LIKE :nazwisko ")
    List<RejestrGruntow> searchByOjciecAndWlscicielAndNazwisko(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndOjciec(@Param("jednostka") String jednostka,@Param("ojciec") String ojciec);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndJednostkaAndWlasciciel(@Param("ojciec") String ojciec,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndJednostkaAndWlascicielAndNazwisko(@Param("ojciec") String ojciec,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            "AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndGminaAndMiejscowoscAndDzialka(@Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndGminaAndMiejscowoscAndDzialka(@Param("matka") String matka, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByGminaAndMiejscowoscAndDzialka(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  ")
    List<RejestrGruntow> searchByGminaAndMiejscowosc(@Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.TERYT_NUMBER  LIKE :teryt  " +
            " AND  p.imie_matki like :matka " +
            "AND  p.imie_ojca like :ojciec    and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndDzialkaAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka, @Param("teryt") String teryt);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndJednostkaAndDzialka(@Param("ojciec") String ojciec, @Param("jednostka") String jednostka,@Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and   p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndJednostkaAndTeryt(@Param("ojciec") String ojciec, @Param("jednostka") String jednostka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel  AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndDzialka(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndNazwiskoAndDzialka(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndTeryt(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndNazwiskoAndTeryt(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndGmina(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndNazwiskoAndGmina(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndMiejscowosc(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByOjciecAndWlascicielAndNazwiskoAndMiejscowosc(@Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND  p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndTerytAndDzialka(@Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND  p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndGminaAndDzialka(@Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND  p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByOjciecAndMiejscowoscAndDzialka(@Param("ojciec") String ojciec, @Param("miejsc") String miejsc, @Param("dzialka") String dzialka);

    //MATKA
    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatka(@Param("matka") String matka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndDzialka(@Param("matka") String matka,@Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  p.imie_matki like :matka " +
            " AND   t.TERYT_NUMBER  LIKE :teryt)")
    List<RejestrGruntow> searchByMatkaAndTeryt(@Param("matka") String matka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_matki like :matka  AND p.IMIE LIKE :wlasciciel")
    List<RejestrGruntow> searchByMatkaAndWlsciciel(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_matki like :matka  AND (p.IMIE_OJCA <> '' and p.imie_ojca is not null ) AND p.nazwisko LIKE :nazwisko")
    List<RejestrGruntow> searchByMatkaAndNazwisko(@Param("matka") String matka, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_matki like :matka  AND p.IMIE LIKE :wlasciciel And p.NAZWISKO LIKE :nazwisko ")
    List<RejestrGruntow> searchByMatkaAndWlscicielAndNazwisko(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByJednostkaAndMatka(@Param("jednostka") String jednostka,@Param("matka") String matka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndJednostkaAndWlasciciel(@Param("matka") String matka,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndJednostkaAndWlascicielAndNazwisko(@Param("matka") String matka,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE t.gmina  LIKE :gmina  " +
            "AND  t.miejscowosc  LIKE :miejscowosc  " +
            " AND  p.imie_matki like :matka AND  p.imie_ojca like :ojciec  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka " +
            "AND t.TERYT_NUMBER  LIKE :teryt ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("miejscowosc") String miejscowosc, @Param("dzialka") String dzialka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndJednostkaAndDzialka(@Param("matka") String matka, @Param("jednostka") String jednostka,@Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and   p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndJednostkaAndTeryt(@Param("matka") String matka, @Param("jednostka") String jednostka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel  AND  p.imie_matki like :matka  " +
            " and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndDzialka(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND  p.imie_matki like :matka   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndNazwiskoAndDzialka(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndTeryt(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndNazwiskoAndTeryt(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndGmina(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndNazwiskoAndGmina(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndMiejscowosc(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko  " +
            "AND  p.imie_matki like :matka ")
    List<RejestrGruntow> searchByMatkaAndWlascicielAndNazwiskoAndMiejscowosc(@Param("matka") String matka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND  p.imie_matki like :matka   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndTerytAndDzialka(@Param("matka") String matka, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND  p.imie_matki like :matka   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndGminaAndDzialka(@Param("matka") String matka, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND  p.imie_matki like :matka   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndMiejscowoscAndDzialka(@Param("matka") String matka, @Param("miejsc") String miejsc, @Param("dzialka") String dzialka);


    //MATKA AND OJCICEC

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE  p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjcie(@Param("matka") String matka, @Param("ojciec") String ojciec);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE   p.imie_matki like :matka AND p.imie_ojca like :ojciec " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE   p.imie_matki like :matka AND p.imie_ojca like :ojciec " +
            " AND   t.TERYT_NUMBER  LIKE :teryt)")
    List<RejestrGruntow> searchByMatkaAndOjciecAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE   p.imie_matki like :matka AND p.imie_ojca like :ojciec  AND p.IMIE LIKE :wlasciciel")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlsciciel(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE   p.imie_matki like :matka AND p.imie_ojca like :ojciec  AND (p.IMIE_OJCA <> '' and p.imie_ojca is not null ) AND p.nazwisko LIKE :nazwisko")
    List<RejestrGruntow> searchByMatkaAndOjciecAndNazwisko(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE   p.imie_matki like :matka AND p.imie_ojca like :ojciec  AND p.IMIE LIKE :wlasciciel And p.NAZWISKO LIKE :nazwisko ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlscicielAndNazwisko(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByJednostkaAndMatkaAndOjciec(@Param("jednostka") String jednostka,@Param("matka") String matka, @Param("ojciec") String ojciec);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndJednostkaAndWlasciciel(@Param("matka") String matka, @Param("ojciec") String ojciec,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) and p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndJednostkaAndWlascicielAndNazwisko(@Param("matka") String matka, @Param("ojciec") String ojciec,@Param("jednostka") String jednostka, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) AND   p.imie_matki like :matka " +
            "AND p.imie_ojca like :ojciec   and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndJednostkaAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("jednostka") String jednostka,@Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND (rg.NR_JEDNOSTKI_REJESTROWEJ  LIKE :jednostka or rg.DATA_ZALOZENIA = :jednostka) " +
            "and    p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndJednostkaAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("jednostka") String jednostka, @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel  AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "WHERE p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("dzialka") String dzialka);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndTeryt(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("teryt") String teryt);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndGmina(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND p.IMIE LIKE :wlasciciel AND p.nazwisko LIKE :nazwisko  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndGmina(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko ,@Param("gmina") String gmina);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndMiejscowosc(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND p.IMIE LIKE :wlasciciel And p.nazwisko LIKE :nazwisko  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec ")
    List<RejestrGruntow> searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndMiejscowosc(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("wlasciciel") String wlasciciel, @Param("nazwisko") String nazwisko , @Param("miejsc") String miejsc);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.TERYT_NUMBER  LIKE :teryt  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndTerytAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("teryt") String teryt, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.GMINA  LIKE :gmina  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndGminaAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("gmina") String gmina, @Param("dzialka") String dzialka);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            "INNER JOIN PERSON_REJESTR_GRUNTOWS prg on rg.id = prg.REJESTR_GRUNTOWS_ID  " +
            "INNER JOIN PERSON p on prg.WLASCICIEL_ID  = p.ID " +
            "INNER JOIN PARCEL d on rg.ID  = d.REJESTR_GRUNTOW_ID " +
            "INNER JOIN TERYT t on d.TERYT_ID = t.ID " +
            "WHERE  t.MIEJSCOWOSC  LIKE :miejsc  " +
            "AND   p.imie_matki like :matka AND p.imie_ojca like :ojciec   " +
            "and d.PARCEL_NUMBER  LIKE :dzialka")
    List<RejestrGruntow> searchByMatkaAndOjciecAndMiejscowoscAndDzialka(@Param("matka") String matka, @Param("ojciec") String ojciec, @Param("miejsc") String miejsc, @Param("dzialka") String dzialka);


    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT rg.* FROM REJESTR_GRUNTOW rg " +
            " WHERE rg.nr_jednostki_rejestrowej is not null " +
            "  %:offset%")
    List<RejestrGruntow> findLimited(@Param("offset") int offset);
}
