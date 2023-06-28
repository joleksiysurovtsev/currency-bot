package o.sur.app.currencybot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["o.sur.app.currencybot"])
class CurrencyBotApplication {
    companion object {
        @JvmStatic
        @Suppress("SpreadOperator")
        fun main(args: Array<String>) {
            runApplication<CurrencyBotApplication>(*args)
        }
    }
}
