
package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;

import java.util.List;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);

    // SERVICE 1
    ReservationResponseDTO affecterReservationAClient(
            Long idReservation,
            String nom,
            String prenom
    );

    // SERVICE 5
    ReservationResponseDTO creerReservationEtContratAssocie(
            ReservationWithContratRequestDTO requestDTO
    );
}
