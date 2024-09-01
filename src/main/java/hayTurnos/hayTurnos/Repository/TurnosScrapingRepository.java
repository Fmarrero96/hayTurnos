package hayTurnos.hayTurnos.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import hayTurnos.hayTurnos.Util.DateUtil;
import hayTurnos.hayTurnos.dto.AvailableCourt;
import hayTurnos.hayTurnos.dto.CanchaDisponibleResponse;
import hayTurnos.hayTurnos.dto.Response;

@Repository
public class TurnosScrapingRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    private MyTelegramBotRepository myTelegramBotRepository;
    

    @Autowired
    public TurnosScrapingRepository(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public List<AvailableCourt> obtenerTurnos() throws Exception {
        // Crear los headers y agregar el origen simulado
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "es-419,es;q=0.9,en;q=0.8");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/json");
        headers.set("Host", "alquilatucancha.com");
        headers.set("Origin", "https://atcsports.io");
        headers.set("Referer", "https://atcsports.io/");
        headers.set("Sec-CH-UA", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
        headers.set("Sec-CH-UA-Mobile", "?0");
        headers.set("Sec-CH-UA-Platform", "\"macOS\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "cross-site");
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");

        // Crear la entidad de la solicitud con los headers
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String fecha = this.myTelegramBotRepository.getFechaBusqueda();
        String url = "https://alquilatucancha.com/api/v3/availability/sportclubs/1003?date=" + fecha;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String json = cortarHastaAvailableCourtsYEliminarUltimo(responseEntity.getBody());
        Response response = objectMapper.readValue(json, Response.class);
        return response.getAvailableCourts();
    }

    public List<CanchaDisponibleResponse> getCanchaDisponibles() throws Exception{
        String fecha = this.myTelegramBotRepository.getFechaBusqueda();
        List<String> horarioList = this.myTelegramBotRepository.getHorario();

        List<String> fechas = horarioList.stream().map(horario -> fecha + horario).collect(Collectors.toList());
        
        List<CanchaDisponibleResponse> hayDisponible = new ArrayList<>();
        List<AvailableCourt>  todosLosTurnos = this.obtenerTurnos();
        todosLosTurnos.forEach(turno -> {
            turno.getAvailableSlots().forEach(
                cancha -> {
                   if (fechas.contains(cancha.getStart())){
                    CanchaDisponibleResponse canchaDisponibleResponse = new CanchaDisponibleResponse(turno.getName(),cancha.getStart());
                    hayDisponible.add(canchaDisponibleResponse);
                   }
                }
            );
        });
        return hayDisponible;
    }

    public String obtenerTurnosPorHorarioString() throws Exception{
        return this.construirStringCanchas(this.getCanchaDisponibles());
    }


    public String construirStringCanchas(List<CanchaDisponibleResponse> canchas) {
        StringBuilder resultado = new StringBuilder();

        for (CanchaDisponibleResponse cancha : canchas) {
            resultado.append(cancha.getNombre())
                     .append("   Horario: ").append(DateUtil.formatoHorarioResponse(cancha.getHorario()))
                     .append("\n");
        }

        return resultado.toString();
    }

    public static String cortarHastaAvailableCourtsYEliminarUltimo(String input) {
        // Busca la posición donde empieza la subcadena "available_courts"
        int index = input.indexOf("available_courts");
        
        // Verifica si la subcadena se encuentra en el input
        if (index != -1) {
            // Extrae la subcadena desde el principio hasta el inicio de "available_courts"
            String resultado = input.substring(index, input.length());
            
            return  "{" + "\"" + resultado;
        }
        
        // Si "available_courts" no se encuentra, retorna el string original
        return input;
    }

}
