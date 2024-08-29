package hayTurnos.hayTurnos.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanchaDisponibleResponse {

    public CanchaDisponibleResponse(String nombre,String horario) {
        this.nombre = nombre;
        this.horario = horario;
    }
    String nombre;
    String horario;


}
