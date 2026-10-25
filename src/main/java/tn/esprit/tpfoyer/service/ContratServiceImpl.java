
package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IContratRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    // CREATE
    @Override
    @Transactional
    public Contrat create(Contrat contrat) {

        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant"
            );
        }

        verifierMontant(contrat);

        // Associer chaque paiement au contrat
        if (contrat.getPaiements() != null) {
            for (Paiement paiement : contrat.getPaiements()) {
                paiement.setContrat(contrat);
            }
        }

        // Enregistrer le contrat et ses paiements
        return contratRepository.save(contrat);
    }

    // READ ONE
    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id)
                );
    }

    // READ ALL
    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    // UPDATE
    @Override
    @Transactional
    public Contrat update(Long id, Contrat contrat) {

        Contrat existant = findById(id);

        verifierMontant(contrat);

        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        existant.setReservation(contrat.getReservation());
        existant.setClient(contrat.getClient());

        return contratRepository.save(existant);
    }

    // DELETE WITH CASCADE

    @Override
    @Transactional
    public void deleteById(Long id) {

        Contrat contrat = findById(id);

        if (contrat.getPaiements() != null) {
            contrat.getPaiements().clear();
        }

        contratRepository.flush();
        contratRepository.delete(contrat);
    }


    // VALIDATION
    private void verifierMontant(Contrat contrat) {

        if (contrat.getMontantTotal() == null ||
                contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Le montant total doit être positif ou nul"
            );
        }
    }
}
