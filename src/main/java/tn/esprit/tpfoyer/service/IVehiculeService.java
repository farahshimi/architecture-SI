
package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.web.dto.VehiculeDTO;

import java.util.List;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);

    // Service 2 - Affecter un véhicule à un équipement
    VehiculeDTO affecterVehiculeAEquipement(
            Long idVehicule,
            Long idEquipement
    );

    // Service 3 - Affecter une maintenance à un véhicule
    VehiculeDTO affecterMaintenanceAvehicule(
            String description,
            String immatriculation
    );
}
