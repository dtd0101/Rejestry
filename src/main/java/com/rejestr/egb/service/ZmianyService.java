package com.rejestr.egb.service;

import com.rejestr.egb.entity.Zmiana;
import com.rejestr.egb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZmianyService {

    @Autowired
    SkorowidzDzialekRepo skorowidzDzialekRepo;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    SkanyRepo skanyRepo;

    @Autowired
    ZmianaRepo zmianaRepo;

    @Autowired
    RejestrGruntowRepo rejestrGruntowRepo;

    public ZmianyService(SkorowidzDzialekRepo skorowidzDzialekRepo, PersonRepo personRepo, SkanyRepo skanyRepo, ZmianaRepo zmianaRepo, RejestrGruntowRepo rejestrGruntowRepo) {
        this.skorowidzDzialekRepo = skorowidzDzialekRepo;
        this.personRepo = personRepo;
        this.skanyRepo = skanyRepo;
        this.zmianaRepo = zmianaRepo;
        this.rejestrGruntowRepo = rejestrGruntowRepo;
    }

    public List<Zmiana> findAll(){
        return zmianaRepo.findAll();
    }

    public List<Zmiana> findBy(String nrZmiany, String jedRejestrowa, String wlasciel, String dzialki, String gmina, String miejscowosc) {
        if (nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findAll();
        } else if (!nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByNrZmiany(nrZmiany);
        } else if (nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRej(jedRejestrowa);
        }else if (nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByGmina(gmina);
        }else if (nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByMiejscowosc(miejscowosc);
        }else if (nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByGminaAndMiejscowosc(gmina,miejscowosc);
        }else if (nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndGmina(jedRejestrowa,gmina);
        }else if (nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndMiejscowosc(jedRejestrowa,miejscowosc);
        }else if (nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndMiejscowoscAndGmina(jedRejestrowa,miejscowosc,gmina);
        }else if (!nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && !gmina.isEmpty() &&  !miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndZmianaAndMiejscowoscAndGmina(jedRejestrowa,nrZmiany,miejscowosc,gmina);
        }else if (!nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndZmianaAndGmina(jedRejestrowa,nrZmiany,gmina);
        }else if (!nrZmiany.isEmpty() && !jedRejestrowa.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByJednostkaRejAndZmiana(jedRejestrowa,nrZmiany);
        }else if (!nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByZmianaAndMiejscowosc(nrZmiany, miejscowosc);
        }else if (!nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty()) {
            return zmianaRepo.findByZmianaAndGmina(nrZmiany, gmina);
        }else if (!nrZmiany.isEmpty() && jedRejestrowa.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty()) {
            return zmianaRepo.findByZmianaAndMiejscowoscAndGmina(nrZmiany,miejscowosc, gmina);
        }
        return null;
    }

    public void saveZmiana(Zmiana zmiana){
        zmianaRepo.save(zmiana);
    }

    public void deleteZmiana(Zmiana zmiana){
        zmianaRepo.delete(zmiana);
    }

}
