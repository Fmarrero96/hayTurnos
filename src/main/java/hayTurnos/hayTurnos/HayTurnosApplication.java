package hayTurnos.hayTurnos;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import hayTurnos.hayTurnos.Service.MyTelegramBot;

@SpringBootApplication
@EnableScheduling
public class HayTurnosApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(HayTurnosApplication.class, args);
		try {
            // Crea una instancia de TelegramBotsApi
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            
            // Obtén el bean MyTelegramBot gestionado por Spring
            MyTelegramBot myTelegramBot = context.getBean(MyTelegramBot.class);
            
            // Registra el bot con Telegram
            botsApi.registerBot(myTelegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

}
