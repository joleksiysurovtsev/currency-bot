package o.sur.app.moviesbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["o.sur.app.moviesbot"])
class MoviesBotApplication {
    companion object {
        @JvmStatic
        @Suppress("SpreadOperator")
        fun main(args: Array<String>) {
            runApplication<MoviesBotApplication>(*args)
        }
    }
}
