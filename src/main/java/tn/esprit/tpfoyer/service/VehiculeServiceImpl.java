package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant"
            );
        }

        verifierVehicule(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id)
                );
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);

        verifierVehicule(vehicule);

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }

    private void verifierVehicule(Vehicule vehicule) {

        if (vehicule.getImmatriculation() == null ||
                vehicule.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException(
                    "L'immatriculation est obligatoire"
            );
        }

        if (vehicule.getMarque() == null ||
                vehicule.getMarque().isBlank()) {
            throw new IllegalArgumentException(
                    "La marque est obligatoire"
            );
        }

        if (vehicule.getModele() == null ||
                vehicule.getModele().isBlank()) {
            throw new IllegalArgumentException(
                    "Le modèle est obligatoire"
            );
        }

        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException(
                    "La catégorie est obligatoire"
            );
        }

        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException(
                    "Le statut est obligatoire"
            );
        }

        if (vehicule.getTarifJournalier() == null ||
                vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier doit être positif ou nul"
            );
        }
    }
}
