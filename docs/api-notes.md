
# Atelier 5 – Spring MVC & SpringDoc

## A. Architecture REST

Le projet TP Foyer est développé avec Spring Boot, Java 17 et Maven.

L'application suit une architecture en couches :

- Controller : reçoit les requêtes HTTP et retourne les réponses.
- Service : contient la logique métier.
- Repository : assure l'accès aux données avec Spring Data JPA.
- Entity : représente les objets métier et leurs relations.

L'application utilise une base de données MySQL.

## B. Endpoints REST

Les contrôleurs REST utilisent les annotations Spring MVC :
@RestController, @RequestMapping, @GetMapping, @PostMapping,
@PutMapping et @DeleteMapping.

| Ressource | URL | Méthodes |
|---|---|---|
| Agence | /api/agences | GET, POST, PUT, DELETE |
| Contrat | /api/contrats | GET, POST, PUT, DELETE |
| Employé | /api/employes | GET, POST, PUT, DELETE |
| Véhicule | /api/vehicules | GET, POST, PUT, DELETE |
| Équipement | /api/equipements | GET, POST, PUT, DELETE |
| Client | /api/clients | GET, POST, PUT, DELETE |
| Réservation | /api/reservations | GET, POST, PUT, DELETE |
| Maintenance | /api/maintenances | GET, POST, PUT, DELETE |
| Paiement | /api/paiements | GET uniquement |

### Codes HTTP testés

- 200 OK : récupération réussie d'une ressource.
- 201 Created : création réussie d'un contrat.
- 204 No Content : réponse prévue pour les suppressions réussies.
- 404 Not Found : ressource inexistante.
- 405 Method Not Allowed : méthode HTTP non autorisée.

## C. Relations JPA et Jackson

Les entités Contrat et Paiement possèdent une relation bidirectionnelle :

- Contrat : @OneToMany(mappedBy = "contrat")
- Paiement : @ManyToOne

Les annotations Jackson utilisées sont :

- @JsonManagedReference : côté Contrat.
- @JsonBackReference : côté Paiement.

Elles permettent d'éviter une récursion infinie pendant
la sérialisation JSON des relations bidirectionnelles.

La cascade JPA permet d'enregistrer les paiements associés
lors de la création d'un contrat.

Un test POST /api/contrats a permis de créer un contrat
avec deux paiements associés.

## D. Documentation SpringDoc et Swagger

SpringDoc OpenAPI est intégré au projet pour documenter
automatiquement les endpoints REST.

Swagger UI :
http://localhost:8081/swagger-ui/index.html

Documentation OpenAPI JSON :
http://localhost:8081/v3/api-docs

Les neuf contrôleurs utilisent des annotations de documentation
telles que @Tag, @Operation et @ApiResponse.

Swagger UI permet de consulter et de tester les endpoints
directement depuis le navigateur.

Postman permet de créer des collections de requêtes,
d'exécuter des tests HTTP et d'enregistrer les scénarios de test.

Une collection Postman a été exportée dans le dossier docs :
TP Foyer - Atelier 5.postman_collection.json

Pour exécuter les requêtes, la variable baseUrl doit avoir
la valeur http://localhost:8081.

## E. Tests et validation

### Tests Maven

Commande exécutée :
mvn clean test

Résultat : BUILD SUCCESS.

### Tests REST

- GET /api/contrats : 200 OK.
- GET /api/contrats/99999 : 404 Not Found.
- POST /api/contrats : 201 Created.
- POST /api/paiements : 405 Method Not Allowed.
- Création d'un contrat avec deux paiements : réussie.
- Test Postman sur GET /api/contrats : réussi.

### Gestion des erreurs

Une exception ResourceNotFoundException permet de signaler
les ressources inexistantes.

La gestion des exceptions HTTP permet de retourner un code
adapté au client.

## F. Analyse SonarQube

Outil utilisé : SonarQube for IDE dans IntelliJ IDEA.

L'analyse de l'ensemble du projet a été effectuée
le 25 octobre 2026.

Résultats observés :

- Aucune anomalie détectée.
- Aucun Security Hotspot détecté.
- Aucune Taint Vulnerability détectée.

Aucune correction issue de cette analyse n'a été nécessaire.

## G. Conclusion

Cet atelier a permis de mettre en pratique :

- La création d'une API REST avec Spring MVC.
- L'architecture Controller / Service / Repository.
- La gestion des relations JPA.
- La sérialisation JSON avec Jackson.
- La documentation avec SpringDoc OpenAPI et Swagger UI.
- Les tests des endpoints avec Postman.
- L'analyse de la qualité du code avec SonarQube for IDE.

Le projet compile et les tests Maven s'exécutent avec succès.
