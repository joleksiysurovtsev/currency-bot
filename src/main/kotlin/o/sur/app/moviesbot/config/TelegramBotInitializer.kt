package o.sur.app.moviesbot.config

import o.sur.app.moviesbot.service.TelegramBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@Configuration
class TelegramBotInitializer {

    @Bean
    fun telegramBotsApi(myTelegramBot: TelegramBot): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

        try {
            telegramBotsApi.registerBot(myTelegramBot)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

        return telegramBotsApi
    }
}