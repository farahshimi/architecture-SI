package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Reservation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface IReservationRepository
        extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findFirstByDateDebutAndDateFin(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    List<Reservation> findByDateFinLessThanEqual(
            LocalDate date
    );
}