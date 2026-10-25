
package tn.esprit.tpfoyer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.service.IClientService;

import java.util.List;

@Tag(name = "Clients", description = "API de gestion des clients")
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients")
    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @Operation(summary = "Consulter un client par ID")
    @ApiResponse(responseCode = "200", description = "Client trouvé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Client créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Client modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @PutMapping("/{id}")
    public Client update(
            @PathVariable Long id,
            @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponse(responseCode = "204", description = "Client supprimé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
