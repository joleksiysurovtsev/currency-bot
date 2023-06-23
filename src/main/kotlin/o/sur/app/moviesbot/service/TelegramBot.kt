package o.sur.app.moviesbot.service

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
) : TelegramLongPollingBot(botKey) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getBotUsername(): String {
        return this.botName
    }

    override fun getBotToken(): String {
        return botKey
    }

    init {
        val listofCommands: MutableList<BotCommand> = ArrayList()
        listofCommands.add(BotCommand("/start", "get a welcome message"))
        listofCommands.add(BotCommand("/help", "info how to use this bot"))
        listofCommands.add(BotCommand("/settings", "set your preferences"))
        try {
            this.execute(SetMyCommands(listofCommands, BotCommandScopeDefault(), null))
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
                else -> { // Note the block
                    sendMessage(chatId, "sorry command was not recognized")
                }
            }
        }
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
