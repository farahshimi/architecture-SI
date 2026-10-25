
package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.domain.Paiement;
import java.util.List;

public interface IPaiementService {

    List<Paiement> findAll();

    Paiement findById(Long id);

    Paiement create(Paiement paiement);

    Paiement update(Long id, Paiement paiement);

    void deleteById(Long id);
}
