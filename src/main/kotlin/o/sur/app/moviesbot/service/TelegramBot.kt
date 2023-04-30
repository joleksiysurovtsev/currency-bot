package o.sur.app.moviesbot.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class TelegramBot(
    @Value("\${bot.name}")
    private val botName: String,
    @Value("\${bot.key}")
    private val botKey: String
) : TelegramLongPollingBot(botKey) {


    override fun getBotUsername(): String {
        return this.botName
    }

    override fun getBotToken(): String {
        return botKey
    }

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
        } catch (e: TelegramApiException) {
            logger.info { e.message }
        }
    }
}

private val logger = KotlinLogging.logger {}