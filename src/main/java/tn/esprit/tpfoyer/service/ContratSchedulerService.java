package tn.esprit.tpfoyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.repository.IContratRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratSchedulerService {

    private final IContratRepository contratRepository;

    @Scheduled(cron = "0 0 7 * * *", zone = "Africa/Tunis")
    public void verifierContrats() {

        System.out.println("Verification des contrats...");

        LocalDate aujourdHui = LocalDate.now(
                ZoneId.of("Africa/Tunis"));

        LocalDate limite = aujourdHui.plusDays(2);

        List<Contrat> contrats =
                contratRepository.findByReservation_DateFinBetween(
                        aujourdHui, limite);

        System.out.println("Nombre de contrats concernes : "
                + contrats.size());

        for (Contrat contrat : contrats) {
            System.out.println(
                    "Contrat arrivant a echeance : "
                            + contrat.getIdContrat());
        }

        System.out.println("Verification des contrats terminee.");
    }
}