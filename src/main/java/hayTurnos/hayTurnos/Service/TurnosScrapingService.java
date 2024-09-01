package hayTurnos.hayTurnos.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hayTurnos.hayTurnos.Repository.TurnosScrapingRepository;
import hayTurnos.hayTurnos.dto.CanchaDisponibleResponse;

@Service
public class TurnosScrapingService {

    @Autowired
    private TurnosScrapingRepository turnosScrapingRepository;

    public String obtenerJson() throws Exception {
        return turnosScrapingRepository.obtenerTurnos().toString();
    }

    public List<CanchaDisponibleResponse>  obtenerTurnosPorHorario() throws Exception{
        return turnosScrapingRepository.getCanchaDisponibles();
    }

    public String obtenerTurnosPorHorarioString() throws Exception{
        return turnosScrapingRepository.obtenerTurnosPorHorarioString();
    }

    public String construirStringCanchas(List<CanchaDisponibleResponse> canchas) {
        return turnosScrapingRepository.construirStringCanchas(canchas);
    }

}
