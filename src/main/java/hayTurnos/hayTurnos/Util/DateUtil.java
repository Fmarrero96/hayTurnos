package hayTurnos.hayTurnos.Util;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import hayTurnos.hayTurnos.Constants.CONSTANTS;

public class DateUtil {

    public static String obtenerFechaDeHoy() {
        // Obtener la fecha actual
        LocalDate today = LocalDate.now();
        
        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Formatear la fecha como String
        return today.format(formatter);
    }


    public static String cambiarFormatoFecha(String fecha){
        // Define el formato de entrada (DD-MM-YYYY)
         DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
        // Parsear la fecha de entrada
        LocalDate fechaLocal = LocalDate.parse(fecha, formatoEntrada);
                    
        // Define el formato de salida (YYYY/MM/DD)
        DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaLocal.format(formatoSalida);
    }

    public static String extraerFecha(String input) {
        // Encuentra la posición después de "Fecha:"
        int index = input.indexOf(CONSTANTS.COMANDO_BOT_FECHA) + CONSTANTS.COMANDO_BOT_FECHA.length();
        
        // Extrae la fecha usando substring
        return input.substring(index).trim();
    }

    public static String extraerHorario(String input) {
        // Encuentra la posición después de "Fecha:"
        int index = 0;
        if (input.contains(CONSTANTS.COMANDO_BOT_CAMBIAR_HORARIOS)){
            index = input.indexOf(CONSTANTS.COMANDO_BOT_CAMBIAR_HORARIOS) + CONSTANTS.COMANDO_BOT_CAMBIAR_HORARIOS.length();
        }else {
            index = input.indexOf(CONSTANTS.COMANDO_BOT_AGREGAR_HORARIOS) + CONSTANTS.COMANDO_BOT_AGREGAR_HORARIOS.length();
        }
        
        // Extrae la fecha usando substring
        return input.substring(index).trim();
    }

    public static String formatoHorarioResponse(String input) {
        // Define the input and output formatters
         DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    
        // Parse the input string
        ZonedDateTime dateTime = ZonedDateTime.parse(input, inputFormatter);
                    
        // Format the date-time to the desired output
        return (dateTime.format(outputFormatter));
    }
}
