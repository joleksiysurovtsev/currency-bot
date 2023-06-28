package o.sur.app.currencybot.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import o.sur.app.currencybot.domain.Currency
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApiService(
    @Value("\${exchange-rate.url}") private val url: String,
) {

    private val client = OkHttpClient()
    private val objectMapper = ObjectMapper()

    fun fetchCurrencyRates(): List<Currency>? {
        val request = Request.Builder().url(url).build()

        return client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null

            response.body?.let {
                objectMapper.readValue(it.string(), object : TypeReference<List<Currency>>() {})
            }
        }
    }
}
