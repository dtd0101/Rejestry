package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Skany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkanyRepo extends JpaRepository<Skany, Long> {
}
