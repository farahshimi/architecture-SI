
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.service.IPaiementService;

import java.util.List;

@Tag(name = "Paiements", description = "Consultation des paiements")
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Lister tous les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    @GetMapping
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    @Operation(summary = "Consulter un paiement par ID")
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public Paiement findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }
}
