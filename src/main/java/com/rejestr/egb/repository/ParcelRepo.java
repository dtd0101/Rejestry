package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepo extends JpaRepository<Parcel, Long> {

    @Query(nativeQuery = true, value = "Select * FROM PARCEL p WHERE p.PARCEL_NUMBER = :parcelNumb")
    List<Parcel> getParcelListByParcelNumb(@Param("parcelNumb") String parcelNumb);
}
