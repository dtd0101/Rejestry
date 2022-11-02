package com.rejestr.egb.service;

import com.rejestr.egb.entity.RejestrGruntow;
import com.rejestr.egb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RejestrGService {

    private static final Logger LOGGER = Logger.getLogger(RejestrGService.class.getName());
    @Autowired
    private RejestrGruntowRepo rejestrGruntowRepo;
    @Autowired
    private AdresRepo adresRepo;
    @Autowired
    private ParcelRepo parcelRepo;
    @Autowired
    private SkanyRepo skanyRepo;
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private TerytRepo terytRepo;
    @Autowired
    private ZmianaRepo zmianaRepo;

    public RejestrGService() {
    }

    public RejestrGService(RejestrGruntowRepo rejestrGruntowRepo, AdresRepo adresRepo, ParcelRepo parcelRepo, SkanyRepo skanyRepo, PersonRepo personRepo) {
        this.rejestrGruntowRepo = rejestrGruntowRepo;
        this.adresRepo = adresRepo;
        this.parcelRepo = parcelRepo;
        this.skanyRepo = skanyRepo;
        this.personRepo = personRepo;
    }

    public RejestrGService(RejestrGruntowRepo rejestrGruntowRepo, ParcelRepo parcelRepo, SkanyRepo skanyRepo, PersonRepo personRepo) {
        this.rejestrGruntowRepo = rejestrGruntowRepo;
        this.parcelRepo = parcelRepo;
        this.skanyRepo = skanyRepo;
        this.personRepo = personRepo;
    }

    public RejestrGService(RejestrGruntowRepo rejestrGruntowRepo, AdresRepo adresRepo, ParcelRepo parcelRepo, SkanyRepo skanyRepo, PersonRepo personRepo, TerytRepo terytRepo) {
        this.rejestrGruntowRepo = rejestrGruntowRepo;
        this.adresRepo = adresRepo;
        this.parcelRepo = parcelRepo;
        this.skanyRepo = skanyRepo;
        this.personRepo = personRepo;
        this.terytRepo = terytRepo;
    }

    public List<RejestrGruntow> findAll(){
        return rejestrGruntowRepo.findAll();
    }

    public List<RejestrGruntow> findLimited(int offset){
        Pageable firstPageWithTwoElements = PageRequest.of(0, offset);

        Page<RejestrGruntow> page = rejestrGruntowRepo.findAll(firstPageWithTwoElements);
        return page.get().collect(Collectors.toList());
    }

    private boolean isFilter(String filtr)
        {
        if(filtr != null || !filtr.isEmpty()){
            return true;
        }
        return false;
    }

    public List<RejestrGruntow> findBy(String jednostka, String wlasciciel, String nazwisko,String ojciec, String matka, String gmina, String miejscowosc, String teryt, String dzialka) {
        if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return findLimited(20);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostka(jednostka);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndTeryt(jednostka,teryt);

        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndGmina(jednostka,gmina);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndGminaAndMiejscowosc(jednostka,gmina,miejscowosc);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndGminaAndTeryt(jednostka,gmina,teryt);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndMiejscowosc(jednostka,miejscowosc);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndMiejscowoscAndTeryt(jednostka,miejscowosc,teryt);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndGminaAndMiejscowoscAndDzialka(jednostka,gmina,miejscowosc,dzialka);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndGminaAndDzialka(jednostka,gmina,dzialka);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndTerytAndDzialka(jednostka,teryt,dzialka);

        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlasciciel(wlasciciel);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty() && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwisko(nazwisko);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByDzialka(dzialka);
        } else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlasciciel(jednostka, wlasciciel);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndNazwisko(jednostka, nazwisko);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndDzialka(jednostka, dzialka);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwisko(wlasciciel, nazwisko);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndDzialka(wlasciciel, dzialka);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndDzialka(nazwisko, dzialka);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndDzialka(wlasciciel, nazwisko, dzialka);
        } else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwisko(jednostka,wlasciciel,nazwisko);
        } else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndDzialka(jednostka, wlasciciel, dzialka);
        } else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndNazwiskoAndDzialka(jednostka, nazwisko, dzialka);
        } else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndDzialka(jednostka, wlasciciel, nazwisko, dzialka);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByTeryt(teryt);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByTerytAndGmina(teryt,gmina);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByGminaAndMiejscowosc(gmina, miejscowosc);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByGmina(gmina);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByGminaAndMiejscowoscAndDzialka(gmina, miejscowosc,dzialka);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMiejscowosc(miejscowosc);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMiejscowoscAndDzialka(miejscowosc,dzialka);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByTerytAndMiejscowosc(teryt,miejscowosc);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByTerytAndMiejscowoscAndGmina(teryt,miejscowosc,gmina);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByDzialkaAndTeryt(teryt, dzialka);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndDzialkaAndTeryt(wlasciciel,teryt, dzialka);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndDzialkaAndTeryt(wlasciciel,nazwisko,teryt, dzialka);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndTeryt(wlasciciel,nazwisko,teryt);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndGmina(wlasciciel,nazwisko,gmina);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMiejscowosc(wlasciciel,nazwisko,miejscowosc);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMiejscowosc(nazwisko,miejscowosc);
        } else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndGminaAndMiejscowosc(wlasciciel,nazwisko,gmina,miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndDzialkaAndTeryt(jednostka, wlasciciel, teryt, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndDzialkaAndTeryt(jednostka, wlasciciel,nazwisko, teryt, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndOjciecAndDzialkaAndTeryt(jednostka, wlasciciel,ojciec, teryt, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  &&  !ojciec.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndDzialkaAndTeryt(jednostka, wlasciciel, nazwisko,ojciec, teryt, dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndOjciecAndDzialkaAndTeryt(wlasciciel,ojciec, teryt, dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndOjciecAndDzialkaAndTeryt(wlasciciel,nazwisko,ojciec, teryt, dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc(wlasciciel,nazwisko,matka,ojciec, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(wlasciciel,nazwisko,matka,ojciec, gmina, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(wlasciciel,matka,ojciec, gmina, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndMatkaAndOjciecAndGminaAndMiejscowosc(wlasciciel,matka,ojciec, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(wlasciciel,nazwisko,matka,ojciec,gmina,miejscowosc, dzialka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndMatkaAndOjciecAndDzialkaAndTeryt(wlasciciel,matka,ojciec, teryt,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt(wlasciciel,nazwisko,matka,ojciec, dzialka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndDzialka(wlasciciel,nazwisko,matka,ojciec,gmina, dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByWlascicielAndNazwiskoAndMatkaAndOjciecAndMiejscowoscAndDzialka(wlasciciel,nazwisko,matka,ojciec,miejscowosc, dzialka);


        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndOjciecAndDzialkaAndTeryt(nazwisko,ojciec, dzialka, teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc(nazwisko,matka,ojciec, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(nazwisko,matka,ojciec, gmina, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(nazwisko,matka,ojciec, gmina, miejscowosc,dzialka,teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt(nazwisko,matka,ojciec, dzialka,teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndGminaAndDzialka(nazwisko,matka,ojciec, gmina,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndMiejscowoscAndDzialka(nazwisko,matka,ojciec, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndGmina(nazwisko,matka,ojciec, gmina);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByNazwiskoAndMatkaAndOjciecAndMiejscowosc(nazwisko,matka,ojciec, miejscowosc);


        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndDzialkaAndTeryt(ojciec, dzialka, teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndGminaAndMiejscowosc(matka,ojciec, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(matka,ojciec, gmina, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(matka,ojciec, gmina, miejscowosc,dzialka,teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndDzialkaAndTeryt(matka,ojciec, dzialka,teryt);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndGmina(matka,ojciec, gmina);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndMiejscowosc(matka,ojciec, miejscowosc);

        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndGmina(ojciec, gmina);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndGmina(matka, gmina);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndGminaAndMiejscowosc(matka, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndGminaAndMiejscowosc(ojciec, gmina, miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndMiejscowosc(matka, miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndMiejscowosc(ojciec, miejscowosc);

        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndGminaAndMiejscowoscAndDzialka(matka, gmina, miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndGminaAndMiejscowoscAndDzialka(ojciec, gmina, miejscowosc,dzialka);

        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndOjciecAndDzialkaAndTeryt(jednostka, ojciec, teryt, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndOjciecAndDzialka(jednostka, wlasciciel, ojciec, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndDzialka(jednostka, wlasciciel, nazwisko, ojciec, dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndOjciecAndTeryt(jednostka, wlasciciel,ojciec, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndTeryt(jednostka, wlasciciel, nazwisko,ojciec, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndTeryt(jednostka, wlasciciel, nazwisko,matka, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndTeryt(jednostka, wlasciciel, matka, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGmina(jednostka, wlasciciel,nazwisko, matka, gmina);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndMiejscowosc(jednostka, wlasciciel,nazwisko, matka, miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowosc(jednostka, wlasciciel,nazwisko, matka,gmina, miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowoscAndDzialka(jednostka, wlasciciel,nazwisko, matka,gmina, miejscowosc,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndGminaAndMiejscowoscAndDzialkaAndTeryt(jednostka, wlasciciel,nazwisko, matka,gmina, miejscowosc,dzialka,teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndDzialkaAndTeryt(jednostka, wlasciciel, matka,dzialka, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndDzialkaAndTeryt(jednostka, wlasciciel,nazwisko, matka,dzialka, teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndDzialka(jednostka, wlasciciel, matka,dzialka);

        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialka(jednostka, wlasciciel,nazwisko, matka,ojciec,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndOjciecAndDzialka(jednostka, wlasciciel, matka,ojciec,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndDzialkaAndTeryt(jednostka, wlasciciel,nazwisko, matka,ojciec,dzialka,teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndOjciecAndDzialkaAndTeryt(jednostka, wlasciciel, matka,ojciec,teryt,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialkaAndTeryt(jednostka, wlasciciel,nazwisko, matka,ojciec,gmina,miejscowosc,dzialka,teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowoscAndDzialka(jednostka, wlasciciel,nazwisko, matka,ojciec,gmina,miejscowosc,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGminaAndMiejscowosc(jednostka, wlasciciel,nazwisko, matka,ojciec,gmina,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndMiejscowosc(jednostka, wlasciciel,nazwisko, matka,ojciec,miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndGmina(jednostka, wlasciciel,nazwisko, matka,ojciec,gmina);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndOjciecAndTeryt(jednostka, wlasciciel,nazwisko, matka,ojciec,teryt);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndMatkaAndOjciecAndTeryt(jednostka, wlasciciel, matka,ojciec,teryt);



        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndMatkaAndDzialka(jednostka, wlasciciel,nazwisko, matka,dzialka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndGmina(jednostka, wlasciciel, nazwisko,ojciec, gmina);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndMiejscowosc(jednostka, wlasciciel, nazwisko,ojciec, miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciecAndGminaAndMiejscowosc(jednostka, wlasciciel, nazwisko,ojciec,gmina, miejscowosc);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndWlascicielAndNazwiskoAndOjciec(jednostka, wlasciciel, nazwisko,ojciec);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciec(ojciec);
        } else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndDzialka(ojciec,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty() && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndTeryt(ojciec,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlsciciel(ojciec,wlasciciel);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndNazwisko(ojciec,nazwisko);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlscicielAndNazwisko(ojciec,wlasciciel, nazwisko);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndOjciec(jednostka,ojciec);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndJednostkaAndWlasciciel(ojciec,jednostka,wlasciciel);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndJednostkaAndDzialka(ojciec,jednostka,dzialka);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndJednostkaAndTeryt(ojciec,jednostka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndDzialka(ojciec,wlasciciel,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndNazwiskoAndDzialka(ojciec, wlasciciel, nazwisko, dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndTeryt(ojciec,wlasciciel,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndNazwiskoAndTeryt(ojciec,wlasciciel,nazwisko,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndGmina(ojciec,wlasciciel,gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndNazwiskoAndGmina(ojciec, wlasciciel, nazwisko, gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndMiejscowosc(ojciec,wlasciciel,miejscowosc);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndWlascicielAndNazwiskoAndMiejscowosc(ojciec,wlasciciel,nazwisko,miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndTerytAndDzialka(ojciec, teryt, dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndGminaAndDzialka(ojciec, gmina, dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByOjciecAndMiejscowoscAndDzialka(ojciec, miejscowosc, dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatka(matka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndDzialka(matka,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndTeryt(matka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlsciciel(matka,wlasciciel);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndNazwisko(matka,nazwisko);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlscicielAndNazwisko(matka,wlasciciel,nazwisko);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndMatka(jednostka,matka);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndJednostkaAndWlasciciel(matka,jednostka,wlasciciel);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndJednostkaAndWlascicielAndNazwisko(matka,jednostka,wlasciciel,nazwisko);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndJednostkaAndDzialka(matka,jednostka,dzialka);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndJednostkaAndTeryt(matka,jednostka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndDzialka(matka,wlasciciel,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndNazwiskoAndDzialka(matka,wlasciciel,nazwisko,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndTeryt(matka,wlasciciel,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndNazwiskoAndTeryt(matka,wlasciciel,nazwisko,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndGmina(matka,wlasciciel,gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndNazwiskoAndGmina(matka,wlasciciel,nazwisko,gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndMiejscowosc(matka,wlasciciel,miejscowosc);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndWlascicielAndNazwiskoAndMiejscowosc(matka,wlasciciel,nazwisko,miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndTerytAndDzialka(matka,teryt,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndGminaAndDzialka(matka,gmina,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndMiejscowoscAndDzialka(matka,miejscowosc,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjcie(matka,ojciec);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndDzialka(matka,ojciec,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndTeryt(matka,ojciec,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlsciciel(matka,ojciec,wlasciciel);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndNazwisko(matka,ojciec,nazwisko);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlscicielAndNazwisko(matka,ojciec,wlasciciel,nazwisko);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByJednostkaAndMatkaAndOjciec(jednostka,matka,ojciec);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndJednostkaAndWlasciciel(matka,ojciec,jednostka,wlasciciel);
        }else if (!jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndJednostkaAndWlascicielAndNazwisko(matka,ojciec,jednostka,wlasciciel,nazwisko);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndJednostkaAndDzialka(matka,ojciec,jednostka,dzialka);
        }else if (!jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndJednostkaAndTeryt(matka,ojciec,jednostka,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndDzialka(matka,ojciec,wlasciciel,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndDzialka(matka,ojciec,wlasciciel,nazwisko,dzialka);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndTeryt(matka,ojciec,wlasciciel,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndTeryt(matka,ojciec,wlasciciel,nazwisko,teryt);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndGmina(matka,ojciec,wlasciciel,gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndGmina(matka,ojciec,wlasciciel,nazwisko,gmina);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndMiejscowosc(matka,ojciec,wlasciciel,miejscowosc);
        }else if (jednostka.isEmpty() && !wlasciciel.isEmpty() && !nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndWlascicielAndNazwiskoAndMiejscowosc(matka,ojciec,wlasciciel,nazwisko,miejscowosc);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && miejscowosc.isEmpty() && !teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndTerytAndDzialka(matka,ojciec,teryt,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && !gmina.isEmpty() && miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndGminaAndDzialka(matka,ojciec,gmina,dzialka);
        }else if (jednostka.isEmpty() && wlasciciel.isEmpty() && nazwisko.isEmpty()  && !ojciec.isEmpty() && !matka.isEmpty() && gmina.isEmpty() && !miejscowosc.isEmpty() && teryt.isEmpty() && !dzialka.isEmpty()) {
            return rejestrGruntowRepo.searchByMatkaAndOjciecAndMiejscowoscAndDzialka(matka,ojciec,miejscowosc,dzialka);
        }
        return null;
    }

    public  long count(){
        return rejestrGruntowRepo.count();
    }

    public void delete(RejestrGruntow rejestrGruntow){
        rejestrGruntowRepo.delete(rejestrGruntow);
    }

    public void save(RejestrGruntow rejestrGruntow){
        rejestrGruntowRepo.save(rejestrGruntow);
    }

    public List<RejestrGruntow> fetchRejestr(int offset, int limit) {
       return rejestrGruntowRepo.offsetAndLimit(offset, limit);
    }

    public int countRejestry(){
      return   rejestrGruntowRepo.countRejestry();
    }

//    @PostConstruct
//    public void populateTestData(){
//        Skany skanZmiany1 = new Skany(new File("e:\\APLIKACJA_REJESTRY\\opisane próbne\\ZMIANY - opisane\\ZM 1_1.jpg"));
//        Skany skanZmiany2 = new Skany(new File("e:\\APLIKACJA_REJESTRY\\opisane próbne\\ZMIANY - opisane\\ZM 1_2.jpg"));
//        Skany skanZmiany3 = new Skany(new File("e:\\APLIKACJA_REJESTRY\\opisane próbne\\ZMIANY - opisane\\ZM 2_3.jpg"));
//        Skany skanZmiany4 = new Skany(new File("e:\\APLIKACJA_REJESTRY\\opisane próbne\\ZMIANY - opisane\\ZM 2_4.jpg"));
//        List<Skany> skanyListZmiana = Arrays.asList(skanZmiany1,skanZmiany2,skanZmiany3,skanZmiany4);
//        skanyRepo.saveAll(skanyListZmiana);
//
//        Zmiana zmiana = new Zmiana();
//        zmiana.setNrZmiany("1");
//        zmiana.setSkany(skanyListZmiana);
//        zmianaRepo.save(zmiana);
//
//        Zmiana zmiana2 = new Zmiana();
//        zmiana2.setNrZmiany("2");
//        zmiana2.addSkan(skanZmiany3);
//        zmiana2.addSkan(skanZmiany4);
//
//        zmianaRepo.save(zmiana2);
//
//        skanZmiany1.setZmiana(zmiana);
//        skanZmiany2.setZmiana(zmiana);
//        skanZmiany3.setZmiana(zmiana2);
//        skanZmiany4.setZmiana(zmiana2);
//        skanyRepo.saveAll(skanyListZmiana);
//
//        Skany skan1 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_074_074.jpg"));
//        Skany skan2 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_004_071.jpg"));
//        Skany skan3 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_005_072.jpg"));
//        Skany skan4 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_006_073.jpg"));
//        List<Skany> skanyList = Arrays.asList(skan1,skan2,skan3,skan4);
//        skanyRepo.saveAll(skanyList);
//
//        Skany skan5 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_074_074.jpg"));
//        Skany skan6 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_004_071.jpg"));
//        Skany skan7 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_005_072.jpg"));
//        Skany skan8 = new Skany(new File("e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\Bircza Stara REJESTR GRUNTÓW TOM II 70-166 SŁ\\RG_006_073.jpg"));
//        List<Skany> skanyList2 = Arrays.asList(skan5,skan6,skan7,skan8);
//        skanyRepo.saveAll(skanyList2);
//
//        Teryt terytNew = new Teryt("Bircza","Stara Bircza", "181301_2.0026");
//        terytRepo.save(terytNew);
//
//        Parcel parcel1 = new Parcel("131");
//        Parcel parcel2 = new Parcel("143");
//        Parcel parcel3 = new Parcel("321");
//        Parcel parcel4 = new Parcel("444");
//        Parcel parcel5 = new Parcel("505");
//        List<Parcel> parcels = Arrays.asList(parcel1,parcel2,parcel3,parcel4,parcel5);
//
//        Parcel parcel6 = new Parcel("5001/1");
//        Parcel parcel7 = new Parcel("5001/2");
//        Parcel parcel8 = new Parcel("5001/3");
//        List<Parcel> parcels2 = Arrays.asList(parcel6,parcel7,parcel8);
//
//
//        List<Person> personList1 = new ArrayList<>();
//        Person person1 = new Person("Franciszek", "Kurtiak", "Władysław", "Józefa");
//        Person person2 = new Person("Robert", "Kurtiak", "Władysław", "Józefa");
//        Person person3 = new Person("Michał", "Kurtiak", null,null);
//        Person person4 = new Person("Monika", "Kurtiak",null, "Józefa");
//        Person person5 = new Person("Renata", "Kurtiak", "Władysław", null);
//        personList1.add(person1);
//        personList1.add(person2);
//        personList1.add(person3);
//        personList1.add(person4);
//        personList1.add(person5);
//
//        List<Person> personList2 = new ArrayList<>();
//        Person person6 = new Person("Michał", "Kurzec", "Oryst", "Janina");
//        Person person7 = new Person("Katarzyna", "Kurzec", "Oryst", "Janina");
//        Person person8 = new Person("Tomasz", "Kurzec", "Wojciech",null);
//        personList2.add(person6);
//        personList2.add(person7);
//        personList2.add(person8);
//
//        RejestrGruntow rejestrGruntow1  = new RejestrGruntow("70", "IVa", "Nr Ksiegi 1", false,
//                skanyList, parcels, personList1);
//        rejestrGruntow1.setDataZalozenia("1965");
//        rejestrGruntow1.addZmiana(zmiana);
//        rejestrGruntow1.addZmianaDo(zmiana);
//        rejestrGruntowRepo.save(rejestrGruntow1);
//
//
//        RejestrGruntow rejestrGruntow2  = new RejestrGruntow("74", "IVa", "Nr Ksiegi 1", false,
//                skanyList2, parcels2, personList2);
//        rejestrGruntow2.setDataZalozenia("1985");
//        rejestrGruntow2.addZmiana(zmiana2);
//        rejestrGruntow2.addZmianaDo(zmiana2);
//        rejestrGruntowRepo.save(rejestrGruntow2);
//
//        person1.addRejestr(rejestrGruntow1);
//        person1.setZmiana(zmiana);
//        person2.addRejestr(rejestrGruntow1);
//        person3.addRejestr(rejestrGruntow1);
//        person4.addRejestr(rejestrGruntow1);
//        person5.addRejestr(rejestrGruntow1);
//        personRepo.saveAll(personList1);
//
//        parcel1.setRejestrGruntow(rejestrGruntow1);
//        parcel1.setTeryt(terytNew);
//        parcel1.setZmiana(zmiana);
//        parcel2.setRejestrGruntow(rejestrGruntow1);
//        parcel2.setTeryt(terytNew);
//        parcel2.setZmiana(zmiana);
//        parcel3.setRejestrGruntow(rejestrGruntow1);
//        parcel3.setTeryt(terytNew);
//        parcel4.setRejestrGruntow(rejestrGruntow1);
//        parcel4.setTeryt(terytNew);
//        parcel5.setRejestrGruntow(rejestrGruntow1);
//        parcel5.setTeryt(terytNew);
//        parcelRepo.saveAll(parcels);
//
//        terytNew.addParcel(parcel1);
//        terytNew.addParcel(parcel2);
//        terytNew.addParcel(parcel3);
//        terytNew.addParcel(parcel4);
//        terytNew.addParcel(parcel5);
//
//        person6.addRejestr(rejestrGruntow2);
//        person6.setZmiana(zmiana2);
//
//        person7.addRejestr(rejestrGruntow2);
//        person8.addRejestr(rejestrGruntow2);
//        personRepo.saveAll(personList2);
//
//        parcel6.setRejestrGruntow(rejestrGruntow2);
//        parcel6.setTeryt(terytNew);
//        parcel6.setZmiana(zmiana2);
//        parcel7.setRejestrGruntow(rejestrGruntow2);
//        parcel7.setTeryt(terytNew);
//        parcel7.setZmiana(zmiana2);
//        parcel8.setRejestrGruntow(rejestrGruntow2);
//        parcel8.setTeryt(terytNew);
//
//        parcelRepo.saveAll(parcels2);
//
////        terytNew.addParcel(parcel6);
////        terytNew.addParcel(parcel7);
////        terytNew.addParcel(parcel8);
//
//        terytRepo.save(terytNew);
//
//        zmiana.setRejestrGruntow(rejestrGruntow1);
//        zmiana.setDoRejestrGruntow(rejestrGruntow2);
//        zmiana.addPerson(person1);
//        zmiana.addParcel(parcel1);
//        zmiana.addParcel(parcel2);
//
//        zmiana2.setRejestrGruntow(rejestrGruntow2);
//        zmiana2.setDoRejestrGruntow(rejestrGruntow1);
//        zmiana2.addPerson(person6);
//        zmiana2.addParcel(parcel6);
//        zmiana2.addParcel(parcel7);
//        zmianaRepo.save(zmiana);
//        zmianaRepo.save(zmiana2);
//    }
}
