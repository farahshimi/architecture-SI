
package tn.esprit.tpfoyer.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.tpfoyer.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithContratRequestDTO {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private StatutReservation statut;

    private Long idVehicule;
    private Long idClient;

    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;
}
