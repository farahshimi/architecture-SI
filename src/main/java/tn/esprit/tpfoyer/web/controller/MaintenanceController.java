
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "API de gestion des maintenances")
@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister toutes les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    @GetMapping
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une maintenance par ID")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @GetMapping("/{id}")
    public Maintenance findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @PutMapping("/{id}")
    public Maintenance update(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
