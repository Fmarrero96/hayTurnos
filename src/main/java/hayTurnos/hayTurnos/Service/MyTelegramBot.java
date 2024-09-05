package hayTurnos.hayTurnos.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import hayTurnos.hayTurnos.Constants.CONSTANTS;
import hayTurnos.hayTurnos.Repository.MyTelegramBotRepository;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private MyTelegramBotRepository myTelegramBotRepository;

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (this.myTelegramBotRepository.esUsuarioConfiguracion(chatId)) {
                String msj = "";
                if(messageText.equalsIgnoreCase(CONSTANTS.COMANDO_BOT_PRENDER)  || messageText.equalsIgnoreCase(CONSTANTS.COMANDO_BOT_APAGAR)){
                    this.myTelegramBotRepository.cambiarEstadoDeBot(messageText);
                    msj = "Se actualizo el estado de bot a " + messageText;
                }else if (messageText.contains(CONSTANTS.COMANDO_BOT_FECHA)){
                    this.myTelegramBotRepository.cambiarFechaDeBusqueda(messageText);
                    msj = "Se actualizo la fecha de busqueda a " + messageText;
                }else if (messageText.contains(CONSTANTS.COMANDO_BOT_CAMBIAR_HORARIOS)){
                    this.myTelegramBotRepository.cambiarHorarioDeBot(messageText);
                    msj = "Se actualizo los horarios de busqueda";
                }else if (messageText.contains(CONSTANTS.COMANDO_BOT_AGREGAR_HORARIOS)){
                    this.myTelegramBotRepository.agregarHorarioDeBot(messageText);
                    msj = "Se actualizo los horarios de busqueda";
                }

                // Aquí envías un string como respuesta
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(msj);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para enviar un mensaje a un usuario específico
    public void enviarMensajeAUsuario(String texto,String usuario) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(usuario)); // ID del usuario
        message.setText("¡Hola! Estos turnos hay disponibles en los horarios que te interesa: \n" + texto); // Texto del mensaje

        try {
            execute(message); // Enviar el mensaje
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public Boolean getEstado(){
        return this.myTelegramBotRepository.getEstado();
    }
}
