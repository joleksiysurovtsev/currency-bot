package o.sur.app.moviesbot.service

import o.sur.app.moviesbot.domain.Currency
import o.sur.app.moviesbot.utils.SpringUtils
import o.sur.app.moviesbot.utils.error
import o.sur.app.moviesbot.utils.info
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class TelegramBot(
    @Value("\${bot.name}")
    private val botName: String,
    @Value("\${bot.key}")
    private val botKey: String,
    private val apiService: ApiService,
) : TelegramLongPollingBot(botKey) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getBotUsername(): String = this.botName

    override fun getBotToken(): String = botKey

    init {
        val listCommands = mutableListOf(
            BotCommand("/start", "get a welcome message"),
            BotCommand("/get_exchange_rate", "get exchange rate"),
            BotCommand("/help", "info how to use this bot"),
            BotCommand("/settings", "set your preferences"),
        )
        try {
            this.execute(SetMyCommands(listCommands, BotCommandScopeDefault(), null))
        } catch (e: TelegramApiException) {
            logger.error("Error setting bot's command list: " + e.message)
        }
    }

    /**
     * This method is called when receiving updates via GetUpdates method
     * */
    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            val userName = update.message.chat.firstName
            when (messageText) {
                "/start" -> startCommandReceived(chatId, userName)
                "/get_exchange_rate" -> getCurrentRate(chatId)
                else -> { // Note the block
                    sendMessage(chatId, "sorry command was not recognized")
                }
            }
        }
    }

    private fun getCurrentRate(chatId: Long?) {
        val currencyRates: List<Currency>? = apiService.fetchCurrencyRates()
        sendMessage(chatId!!, SpringUtils.performCurrencyRatesMassage(currencyRates))
    }

    fun startCommandReceived(chatId: Long, userName: String) {
        sendMessage(chatId, "Hello $userName")
    }

    fun sendMessage(chatId: Long, message: String) {
        val sendMessage = SendMessage()
        sendMessage.setChatId(chatId)
        sendMessage.text = message

        try {
            execute(sendMessage)
            logger.info { "response message: $message" }
        } catch (e: TelegramApiException) {
            logger.error { e.message.toString() }
        }
    }
}
