package o.sur.app.currencybot.utils

import com.vdurmont.emoji.EmojiParser
import o.sur.app.currencybot.domain.Currency
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DOLLAR = "USD"
private const val EURO = "EUR"

object SpringUtils {

    fun performCurrencyRatesMassage(currencyRates: List<Currency>?): String {
        val currencyExchange: String = EmojiParser.parseToUnicode("\uD83D\uDCB1")
        val dollar: String = EmojiParser.parseToUnicode(":dollar:")
        val euro: String = EmojiParser.parseToUnicode(":euro:")
        val flagUa: String = EmojiParser.parseToUnicode(":ua:")

        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        val buyRateEuro = currencyRates?.first { it.currency.contains(EURO) }?.buyRate
        val saleRateEuro = currencyRates?.first { it.currency.contains(EURO) }?.saleRate
        val buyRateDollar = currencyRates?.first { it.currency.contains(DOLLAR) }?.buyRate
        val saleRateDollar = currencyRates?.first { it.currency.contains(DOLLAR) }?.saleRate

        return "$currencyExchange Exchange rate $flagUa $currentDate \n" +
            "$euro euro buy: $buyRateEuro sale: $saleRateEuro \n" +
            "$dollar dollar buy: $buyRateDollar sale: $saleRateDollar \n"
    }
}
