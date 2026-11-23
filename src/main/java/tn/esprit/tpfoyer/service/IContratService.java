
package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.web.dto.ContratDTO;

import java.time.LocalDate;
import java.util.List;

public interface IContratService {

    Contrat create(Contrat contrat);

    Contrat findById(Long id);

    List<Contrat> findAll();

    Contrat update(Long id, Contrat contrat);

    void deleteById(Long id);

    // ATELIER 7 - SERVICE 4
    ContratDTO affecterContratAReservation(
            LocalDate dateSignature,
            LocalDate dateDebut,
            LocalDate dateFin
    );
}
