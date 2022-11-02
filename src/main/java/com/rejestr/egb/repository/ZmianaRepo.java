package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Zmiana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZmianaRepo extends JpaRepository<Zmiana, Long> {

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByNrZmiany(@Param("nrZmiany") String nrZmiany);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRej(@Param("jedRejestrowa") String jedRejestrowa);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.GMINA  LIKE :gmina" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByGmina(@Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByMiejscowosc(@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByGminaAndMiejscowosc(@Param("gmina") String gmina,@Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "and t.GMINA  LIKE :gmina" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndGmina(@Param("jedRejestrowa") String jedRejestrowa, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndMiejscowosc(@Param("jedRejestrowa") String jedRejestrowa, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina " +
                    "ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndMiejscowoscAndGmina(@Param("jedRejestrowa") String jedRejestrowa, @Param("miejscowosc") String miejscowosc, @Param("gmina") String gmina);


       @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "and t.GMINA  LIKE :gmina " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndZmianaAndGmina(@Param("jedRejestrowa") String jedRejestrowa,@Param("nrZmiany") String nrZmiany, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndZmiana(@Param("jedRejestrowa") String jedRejestrowa,@Param("nrZmiany") String nrZmiany);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE r.NR_JEDNOSTKI_REJESTROWEJ LIKE :jedRejestrowa " +
                    "and t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByJednostkaRejAndZmianaAndMiejscowoscAndGmina(@Param("jedRejestrowa") String jedRejestrowa,@Param("nrZmiany") String nrZmiany, @Param("miejscowosc") String miejscowosc, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByZmianaAndMiejscowosc(@Param("nrZmiany") String nrZmiany, @Param("miejscowosc") String miejscowosc);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.GMINA  LIKE :gmina " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany " +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByZmianaAndGmina(@Param("nrZmiany") String nrZmiany, @Param("gmina") String gmina);

    @Query(nativeQuery = true, value =
            "Select DISTINCT z.* " +
                    "FROM ZMIANA z " +
                    "INNER JOIN REJESTR_GRUNTOW r on z.REJESTR_GRUNTOW_ID = r.ID " +
                    "Inner join parcel p on r.id = p.rejestr_gruntow_id " +
                    "Inner Join teryt t on p.teryt_id = t.id " +
                    "WHERE t.MIEJSCOWOSC  LIKE :miejscowosc " +
                    "and t.GMINA  LIKE :gmina " +
                    "And z.NR_ZMIANY  LIKE :nrZmiany" +
                    " ORDER BY t.miejscowosc ASC")
    List<Zmiana> findByZmianaAndMiejscowoscAndGmina(@Param("nrZmiany") String nrZmiany, @Param("miejscowosc") String miejscowosc, @Param("gmina") String gmina);


}
