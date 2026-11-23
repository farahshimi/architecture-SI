
package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant"
            );
        }

        verifierReservation(reservation);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id)
                );
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existante = findById(id);

        verifierReservation(reservation);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }

    // SERVICE 1 : Affecter une réservation à un client
    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(
            Long idReservation,
            String nom,
            String prenom) {

        Reservation reservation = findById(idReservation);

        Client client = clientRepository
                .findByNomAndPrenom(nom, prenom)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Client introuvable : " + nom + " " + prenom
                        )
                );

        reservation.setClient(client);

        Reservation saved = reservationRepository.save(reservation);

        return convertirEnDTO(saved);
    }

    // SERVICE 5 : Créer une réservation et son contrat associé
    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(
            ReservationWithContratRequestDTO requestDTO) {

        if (requestDTO == null) {
            throw new IllegalArgumentException(
                    "Les données sont obligatoires"
            );
        }

        Client client = clientRepository
                .findById(requestDTO.getIdClient())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client", requestDTO.getIdClient()
                        )
                );

        Vehicule vehicule = vehiculeRepository
                .findById(requestDTO.getIdVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", requestDTO.getIdVehicule()
                        )
                );

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setStatut(requestDTO.getStatut());
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        verifierReservation(reservation);

        Reservation savedReservation =
                reservationRepository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setMontantTotal(requestDTO.getMontantTotal());
        contrat.setValide(requestDTO.isValide());
        contrat.setReservation(savedReservation);
        contrat.setClient(client);

        if (contrat.getDateSignature() == null ||
                contrat.getMontantTotal() == null ||
                contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Date de signature ou montant du contrat invalide"
            );
        }

        contratRepository.save(contrat);

        return convertirEnDTO(savedReservation);
    }

    private ReservationResponseDTO convertirEnDTO(
            Reservation reservation) {

        Client client = reservation.getClient();

        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut() == null
                        ? null
                        : reservation.getStatut().name(),
                client == null ? null : client.getIdClient(),
                client == null ? null : client.getNom(),
                client == null ? null : client.getPrenom()
        );
    }

    private void verifierReservation(Reservation reservation) {

        if (reservation.getDateDebut() == null ||
                reservation.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires"
            );
        }

        if (reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}
