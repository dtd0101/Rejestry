package com.rejestr.egb.service;

import com.rejestr.egb.entity.SkorowidzDzialek;
import com.rejestr.egb.repository.SkanyRepo;
import com.rejestr.egb.repository.SkorowidzDzialekRepo;
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
public class SkorowidzDzService {

    @Autowired
    SkorowidzDzialekRepo skorowidzDzialekRepo;

    @Autowired
    TerytRepo terytRepo;

    @Autowired
    SkanyRepo skanyRepo;

    @Autowired
    ZmianaRepo zmianaRepo;

    public SkorowidzDzService() {
    }

    public SkorowidzDzService(SkorowidzDzialekRepo skorowidzDzialekRepo, TerytRepo terytRepo, SkanyRepo skanyRepo) {
        this.skorowidzDzialekRepo = skorowidzDzialekRepo;
        this.terytRepo = terytRepo;
        this.skanyRepo = skanyRepo;
    }

    public List<SkorowidzDzialek> findAll(){
        return skorowidzDzialekRepo.findAll();
    }


//    @PostConstruct
//    public void populateTestData(){
//
//        Teryt teryt = new Teryt("Bircza","Stara Bircza", "181301_2.0026");
//        terytRepo.save(teryt);
//
//        SkorowidzDzialek skorowidzDzialek = new SkorowidzDzialek();
//        skorowidzDzialekRepo.save(skorowidzDzialek);
//
//        Skany skan1 = new Skany(new File("g:\\APLIKACJA_REJESTRY\\opisane próbne\\1. Bircza\\01. Skorowidz działek 1-Gmina Bircza, Obręb Bircza WL opisane\\01_skorowidz działek .jpg"));
//        Skany skan2 = new Skany(new File("g:\\APLIKACJA_REJESTRY\\opisane próbne\\1. Bircza\\01. Skorowidz działek 1-Gmina Bircza, Obręb Bircza WL opisane\\02_skorowidz działek .jpg"));
//        Skany skan3 = new Skany(new File("g:\\APLIKACJA_REJESTRY\\opisane próbne\\1. Bircza\\01. Skorowidz działek 1-Gmina Bircza, Obręb Bircza WL opisane\\03_skorowidz działek .jpg"));
//        Skany skan4 = new Skany(new File("g:\\APLIKACJA_REJESTRY\\opisane próbne\\1. Bircza\\01. Skorowidz działek 1-Gmina Bircza, Obręb Bircza WL opisane\\04_skorowidz działek .jpg"));
//        List<Skany> skanyList = Arrays.asList(skan1,skan2,skan3,skan4);
//        skan1.setSkorowidzDzialek(skorowidzDzialek);
//        skan2.setSkorowidzDzialek(skorowidzDzialek);
//        skan3.setSkorowidzDzialek(skorowidzDzialek);
//        skan4.setSkorowidzDzialek(skorowidzDzialek);
//        skanyRepo.saveAll(skanyList);
//
//        teryt.addSkorowidz(skorowidzDzialek);
//        terytRepo.save(teryt);
//
//        skorowidzDzialek.setSkany(skanyList);
//        skorowidzDzialek.setTeryt(teryt);
//        skorowidzDzialek.setRok("1965");
//        skorowidzDzialekRepo.save(skorowidzDzialek);
//    }

    public List<SkorowidzDzialek> findBy(String rok,String gmina, String miejscowosc, String teryt) {
        if ((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())) {
            return findLimited();
        } else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByGmina(gmina);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByMiejscowosc(miejscowosc);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByTeryt(teryt);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndMiejscowosc(gmina,miejscowosc);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndTeryt(gmina,teryt);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByMiejscowoscAndTeryt(miejscowosc,teryt);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok == null || rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndMiejscowoscAndTeryt(gmina,miejscowosc,teryt);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByRok(rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndRok(gmina,rok);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByMiejscowoscAndRok(miejscowosc,rok);
        }else if((gmina == null || gmina.isEmpty()) && (miejscowosc == null || miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByTerytAndRok(teryt,rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt == null || teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndMiejscowoscAndRok(gmina,miejscowosc,rok);
        }else if((gmina != null || !gmina.isEmpty()) && (miejscowosc != null || !miejscowosc.isEmpty()) && (teryt != null || !teryt.isEmpty()) && (rok != null || !rok.isEmpty())){
            return skorowidzDzialekRepo.findByGminaAndMiejscowoscAndTerytAndRok(gmina,miejscowosc,teryt,rok);
        }
        return null;
    }

    public List<SkorowidzDzialek> findLimited() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 20);

        Page<SkorowidzDzialek> page = skorowidzDzialekRepo.findAll(firstPageWithTwoElements);
        return page.get().collect(Collectors.toList());
    }
}
