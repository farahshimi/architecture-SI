
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.service.IEmployeService;

import java.util.List;

@Tag(name = "Employés", description = "API de gestion des employés")
@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "Consulter un employé par ID")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @GetMapping("/{id}")
    public Employe findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Lister tous les employés")
    @ApiResponse(responseCode = "200", description = "Liste des employés")
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @PutMapping("/{id}")
    public Employe update(
            @PathVariable Long id,
            @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
