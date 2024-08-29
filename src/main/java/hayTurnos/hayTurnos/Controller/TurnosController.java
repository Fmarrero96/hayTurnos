package hayTurnos.hayTurnos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hayTurnos.hayTurnos.Service.TurnosScrapingService;
import hayTurnos.hayTurnos.dto.AvailableSlot;
import hayTurnos.hayTurnos.dto.CanchaDisponibleResponse;


@RestController
public class TurnosController {

    @Autowired
    private TurnosScrapingService turnosScrapingService;

    @GetMapping("/scrape")
    public String obtenerDatos() throws Exception {
        return turnosScrapingService.obtenerJson();
    }

    @GetMapping("/porHorario")
    public List<CanchaDisponibleResponse> obtenerTurnosPorHorario() throws Exception {
        return turnosScrapingService.obtenerTurnosPorHorario();
    }
}
