package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.domain.*;
import tn.esprit.tpfoyer.repository.IReservationRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeSchedulerService {

    private final IReservationRepository reservationRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 * * *", zone = "Africa/Tunis")
    public void libererVehicules() {

        System.out.println("Verification des reservations...");

        LocalDate aujourdHui = LocalDate.now(
                ZoneId.of("Africa/Tunis"));

        List<Reservation> reservations =
                reservationRepository.findByDateFinLessThanEqual(
                        aujourdHui);

        int compteur = 0;

        for (Reservation reservation : reservations) {

            if (reservation.getStatut() == StatutReservation.CONFIRMEE
                    && reservation.getDateFin().isBefore(aujourdHui)) {

                reservation.setStatut(StatutReservation.TERMINEE);

                Vehicule vehicule = reservation.getVehicule();

                if (vehicule != null
                        && vehicule.getStatut() == StatutVehicule.LOUE) {

                    boolean autreLocation = vehicule.getReservations()
                            .stream()
                            .anyMatch(r ->
                                    r.getStatut() == StatutReservation.CONFIRMEE
                                            && !r.getDateDebut().isAfter(aujourdHui)
                                            && !r.getDateFin().isBefore(aujourdHui));

                    if (!autreLocation) {
                        vehicule.setStatut(StatutVehicule.DISPONIBLE);

                        System.out.println("Vehicule disponible : "
                                + vehicule.getImmatriculation());
                    }
                }

                reservationRepository.save(reservation);

                compteur++;

                System.out.println("Reservation terminee : "
                        + reservation.getIdReservation());
            }
        }

        System.out.println("Nombre de reservations terminees : "
                + compteur);

        System.out.println("Verification des reservations terminee.");
    }
}