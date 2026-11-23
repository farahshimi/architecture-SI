
package tn.esprit.tpfoyer.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {

    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    private String categorie;
    private BigDecimal tarifJournalier;
    private String statut;
    private List<Long> idsEquipements;
}
