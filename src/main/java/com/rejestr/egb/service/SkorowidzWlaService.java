package com.rejestr.egb.service;

import com.rejestr.egb.entity.SkorowidzWlascicieli;
import com.rejestr.egb.repository.SkanyRepo;
import com.rejestr.egb.repository.SkorowidzWlaRepo;
import com.rejestr.egb.repository.TerytRepo;
import com.rejestr.egb.repository.ZmianaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkorowidzWlaService {

    @Autowired
    SkorowidzWlaRepo skorowidzWlaRepo;

    @Autowired
    TerytRepo terytRepo;

    @Autowired
    SkanyRepo skanyRepo;

    @Autowired
    ZmianaRepo zmianaRepo;

    public SkorowidzWlaService() {
    }

    public SkorowidzWlaService(SkorowidzWlaRepo skorowidzDzialekRepo, TerytRepo terytRepo, SkanyRepo skanyRepo) {
        this.skorowidzWlaRepo = skorowidzDzialekRepo;
        this.terytRepo = terytRepo;
        this.skanyRepo = skanyRepo;
    }

    public List<SkorowidzWlascicieli> findAll(){
        return skorowidzWlaRepo.findAll();
    }


    public List<SkorowidzWlascicieli> findBy(String rok,String gmina, String miejscowosc, String teryt) {
        if ((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())) {
            return findLimited();
        } else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByGmina(gmina);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByMiejscowosc(miejscowosc);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByTeryt(teryt);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndMiejscowosc(gmina,miejscowosc);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndTeryt(gmina,teryt);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByMiejscowoscAndTeryt(miejscowosc,teryt);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndMiejscowoscAndTeryt(gmina,miejscowosc,teryt);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByRok(rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndRok(gmina,rok);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByMiejscowoscAndRok(miejscowosc,rok);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByTerytAndRok(teryt,rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndMiejscowoscAndRok(gmina,miejscowosc,rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzWlaRepo.findByGminaAndMiejscowoscAndTerytAndRok(gmina,miejscowosc,teryt,rok);
        }
        return null;
    }

    public List<SkorowidzWlascicieli> findLimited() {

            Pageable firstPageWithTwoElements = PageRequest.of(0, 20);

            Page<SkorowidzWlascicieli> page = skorowidzWlaRepo.findAll(firstPageWithTwoElements);
            return page.get().collect(Collectors.toList());

    }
}
