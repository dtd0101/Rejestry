package com.rejestr.egb.repository;

import com.rejestr.egb.entity.Adres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresRepo extends JpaRepository<Adres, Long>
{
}
