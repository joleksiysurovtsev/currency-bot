package o.sur.app.currencybot.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "user")
data class User(
    @Id val chatId: Long,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val createdAt: Timestamp,
)
