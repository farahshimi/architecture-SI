
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;

import java.util.List;

@Tag(
        name = "Réservations",
        description = "API de gestion des réservations"
)
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    // READ ALL
    @Operation(summary = "Lister toutes les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    // READ ONE
    @Operation(summary = "Consulter une réservation par ID")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public Reservation findById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    // CREATE
    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    // UPDATE
    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @PutMapping("/{id}")
    public Reservation update(
            @PathVariable Long id,
            @RequestBody Reservation reservation) {

        return reservationService.update(id, reservation);
    }

    // DELETE
    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reservationService.deleteById(id);
    }

    // ATELIER 7 - SERVICE 1
    @Operation(summary = "Affecter une réservation à un client")
    @ApiResponse(responseCode = "200", description = "Affectation réussie")
    @PutMapping("/{idReservation}/affecter-client")
    public ReservationResponseDTO affecterReservationAClient(
            @PathVariable Long idReservation,
            @RequestParam String nom,
            @RequestParam String prenom) {

        return reservationService.affecterReservationAClient(
                idReservation, nom, prenom
        );
    }

    // ATELIER 7 - SERVICE 5
    @Operation(
            summary = "Créer une réservation et son contrat associé",
            description = "Crée une réservation et un contrat dans une seule transaction"
    )
    @ApiResponse(responseCode = "201", description = "Réservation et contrat créés")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping("/avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @RequestBody ReservationWithContratRequestDTO requestDTO) {

        return reservationService.creerReservationEtContratAssocie(requestDTO);
    }
}
