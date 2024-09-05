package hayTurnos.hayTurnos.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hayTurnos.hayTurnos.Repository.MyTelegramBotRepository;
import hayTurnos.hayTurnos.dto.CanchaDisponibleResponse;

@Service
public class CanchaService {

    @Autowired
    TurnosScrapingService turnosScrapingService;

    @Autowired
    private MyTelegramBotRepository myTelegramBotRepository;

    @Autowired
    MyTelegramBot myTelegramBot;
    // Simula el cache de canchas con nombre y horario
    private List<CanchaDisponibleResponse> cacheCanchas = new ArrayList<>();

    // Ejecuta esta tarea cada 3 minutos
    @Scheduled(fixedRate = 180000)
    public void checkForUpdates() throws Exception {
        if (this.myTelegramBot.getEstado()){
            // Llama al endpoint para obtener datos actualizados
            List<CanchaDisponibleResponse> canchasActualizadas = turnosScrapingService.obtenerTurnosPorHorario();

            // Filtra las canchas que han cambiado
            List<CanchaDisponibleResponse> canchasCambiadas = canchasActualizadas.stream()
                    .filter(canchaNueva -> cacheCanchas.stream().noneMatch(
                            canchaCacheada -> canchaCacheada.equals(canchaNueva)))
                    .collect(Collectors.toList());

            // Imprime un mensaje si hay cambios
            if (!canchasCambiadas.isEmpty()) {
                List<String> usuariosEnviosList = this.myTelegramBotRepository.getUsuariosEnvios();
                String canchasConstruidas = turnosScrapingService.construirStringCanchas(canchasCambiadas);
                usuariosEnviosList.forEach(usuarioEnvio-> {
                    myTelegramBot.enviarMensajeAUsuario(canchasConstruidas,usuarioEnvio);
                });
                // Actualiza el cache con los nuevos datos
                cacheCanchas = canchasActualizadas;
            } else {
                System.out.println("No hay cambios en las canchas.");
            }
        }
    }


}
