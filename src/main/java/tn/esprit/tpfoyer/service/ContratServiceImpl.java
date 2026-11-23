
package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;

import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.repository.IReservationRepository;

import tn.esprit.tpfoyer.web.dto.ContratDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;

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

        if (contrat.getPaiements() != null) {
            for (Paiement paiement : contrat.getPaiements()) {
                paiement.setContrat(contrat);
            }
        }

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

    // SERVICE 4 - Affecter un contrat à une réservation
    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(
            LocalDate dateSignature,
            LocalDate dateDebut,
            LocalDate dateFin) {

        Reservation reservation = reservationRepository
                .findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Aucune réservation trouvée pour ces dates"
                        )
                );

        if (reservation.getContrat() != null) {
            throw new IllegalArgumentException(
                    "Cette réservation possède déjà un contrat"
            );
        }

        Contrat contrat = contratRepository
                .findFirstByDateSignatureAndReservationIsNull(dateSignature)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Aucun contrat disponible pour cette date de signature"
                        )
                );

        contrat.setReservation(reservation);
        contrat.setClient(reservation.getClient());

        Contrat saved = contratRepository.save(contrat);

        return new ContratDTO(
                saved.getIdContrat(),
                saved.getDateSignature(),
                saved.getMontantTotal(),
                saved.isValide(),
                saved.getReservation().getIdReservation(),
                saved.getClient() != null
                        ? saved.getClient().getIdClient()
                        : null
        );
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
