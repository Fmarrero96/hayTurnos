package hayTurnos.hayTurnos.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bot")
public class TelegramBotConfigure {

    Boolean prendido;
    List<String> horarios;
    String fecha;


    public void agregarFechas(List<String> horariosAgregar){
        this.horarios.addAll(horarios);
    }

}
