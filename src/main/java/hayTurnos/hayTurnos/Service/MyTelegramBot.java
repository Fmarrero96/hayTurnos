package hayTurnos.hayTurnos.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private TurnosScrapingService turnosScrapingService;

    @Override
    public String getBotUsername() {
        return "HayTurnosBot";
    }

    @Override
    public String getBotToken() {
        return "7125254690:AAH64adFqO9Sq8N5AfRiWxe4ZBtpYQxUxOY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String msj = "";
            try {
                msj = this.turnosScrapingService.obtenerTurnosPorHorarioString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Aquí envías un string como respuesta
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("¡Hola! Estos turnos hay disponibles en los horarios que te interesa: \n" + msj);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para enviar un mensaje a un usuario específico
    public void enviarMensajeAUsuario(long chatId, String texto) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId)); // ID del usuario
        message.setText(texto); // Texto del mensaje

        try {
            execute(message); // Enviar el mensaje
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
