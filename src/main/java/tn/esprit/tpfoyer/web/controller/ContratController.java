
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.service.IContratService;

import java.util.List;

@Tag(
        name = "Contrats",
        description = "API de gestion des contrats"
)
@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // CREATE - POST - 201 Created
    @Operation(
            summary = "Créer un contrat",
            description = "Enregistre un nouveau contrat"
    )
    @ApiResponse(responseCode = "201", description = "Contrat créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // READ ONE - GET - 200 OK
    @Operation(
            summary = "Rechercher un contrat par ID",
            description = "Retourne un contrat à partir de son identifiant"
    )
    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    // READ ALL - GET - 200 OK
    @Operation(
            summary = "Lister tous les contrats",
            description = "Retourne la liste de tous les contrats enregistrés"
    )
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // UPDATE - PUT - 200 OK
    @Operation(
            summary = "Modifier un contrat",
            description = "Met à jour un contrat existant"
    )
    @ApiResponse(responseCode = "200", description = "Contrat modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contrat) {

        return contratService.update(id, contrat);
    }

    // DELETE - 204 No Content
    @Operation(
            summary = "Supprimer un contrat",
            description = "Supprime un contrat à partir de son identifiant"
    )
    @ApiResponse(responseCode = "204", description = "Contrat supprimé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
