package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Contrat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface IContratRepository
        extends JpaRepository<Contrat, Long> {

    Optional<Contrat> findFirstByDateSignatureAndReservationIsNull(
            LocalDate dateSignature
    );

    List<Contrat> findByReservation_DateFinBetween(
            LocalDate debut,
            LocalDate fin
    );
}