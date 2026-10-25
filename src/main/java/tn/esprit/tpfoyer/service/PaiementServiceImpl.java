
package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    // GET ALL
    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    // GET BY ID
    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id)
                );
    }

    // CREATE
    @Override
    @Transactional
    public Paiement create(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    // UPDATE
    @Override
    @Transactional
    public Paiement update(Long id, Paiement paiement) {
        Paiement existant = findById(id);

        existant.setMontant(paiement.getMontant());
        existant.setDatePaiement(paiement.getDatePaiement());
        existant.setModePaiement(paiement.getModePaiement());
        existant.setContrat(paiement.getContrat());

        return paiementRepository.save(existant);
    }

    // DELETE
    @Override
    @Transactional
    public void deleteById(Long id) {
        Paiement paiement = findById(id);
        paiementRepository.delete(paiement);
    }
}
