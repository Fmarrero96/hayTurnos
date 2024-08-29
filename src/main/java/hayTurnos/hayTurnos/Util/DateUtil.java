package hayTurnos.hayTurnos.Util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String obtenerFechaDeHoy() {
        // Obtener la fecha actual
        LocalDate today = LocalDate.now();
        
        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Formatear la fecha como String
        return today.format(formatter);
    }
}
