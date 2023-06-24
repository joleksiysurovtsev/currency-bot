package o.sur.app.moviesbot.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class Currency(
    @JsonProperty("ccy") var currency: String,
    @JsonProperty("base_ccy") var baseCurrency: String,
    @JsonProperty("buy") var buyRate: BigDecimal,
    @JsonProperty("sale") var saleRate: BigDecimal
)
