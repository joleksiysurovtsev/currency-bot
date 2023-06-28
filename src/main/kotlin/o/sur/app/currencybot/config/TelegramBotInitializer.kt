package o.sur.app.currencybot.config

import o.sur.app.currencybot.service.TelegramBot
import o.sur.app.currencybot.utils.error
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramBotInitializer {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun telegramBotsApi(myTelegramBot: TelegramBot): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

        try {
            telegramBotsApi.registerBot(myTelegramBot)
        } catch (e: TelegramApiException) {
            logger.error { e.message.toString() }
        }
        return telegramBotsApi
    }
}
