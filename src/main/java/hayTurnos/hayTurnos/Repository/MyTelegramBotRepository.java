package hayTurnos.hayTurnos.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import hayTurnos.hayTurnos.Util.DateUtil;
import hayTurnos.hayTurnos.dto.TelegramBotConfigure;

@Repository
public class MyTelegramBotRepository {

    @Autowired
    private TelegramBotConfigure telegramBotConfigure;

    @Value("${telegram.bot.idUsuarioConfiguracion}")
    private String idUsuarioConfiguracion;

    public String getBotIdUsuarioConfiguracion() {
        return idUsuarioConfiguracion;
    }

    public Boolean esUsuarioConfiguracion(Long idUsuario) {
        return this.getBotIdUsuarioConfiguracion().equals(idUsuario.toString());
    }

    public String getFechaBusqueda(){
        return this.telegramBotConfigure.getFecha();
    }

    public void cambiarFechaDeBusqueda(String fecha){
        this.telegramBotConfigure.setFecha(DateUtil.cambiarFormatoFecha(DateUtil.extraerFecha(fecha)));
    }

    public Boolean getEstado(){
        return this.telegramBotConfigure.getPrendido();
    }

    public void cambiarEstadoDeBot(String estado){
        if ("prender".equalsIgnoreCase(estado)){
            this.telegramBotConfigure.setPrendido(true);
        }else if ("apagar".equalsIgnoreCase(estado)){
            this.telegramBotConfigure.setPrendido(false);
        }
    }

    public List<String> getHorario(){
        return this.telegramBotConfigure.getHorarios();
    }

    public void cambiarHorarioDeBot(String horarios){
        String horariosExtraidos = DateUtil.extraerHorario(horarios);
        List<String> nuevosHorarios = Arrays.asList(horariosExtraidos.split(","));
        

        //T21:00-03:00
        nuevosHorarios = nuevosHorarios.stream().map(horario -> "T" + horario + "-03:00").collect(Collectors.toList());

        this.telegramBotConfigure.setHorarios(nuevosHorarios);
    }

    public List<String> agregarHorarioDeBot(String horarios){
        List<String> horariosList = this.getHorario();
        this.cambiarHorarioDeBot(horarios);

        this.telegramBotConfigure.agregarFechas(horariosList);

        return this.getHorario();
    }



}
