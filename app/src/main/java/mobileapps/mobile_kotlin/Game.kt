package mobileapps.mobile_kotlin

import android.icu.text.SimpleDateFormat
import java.io.Serializable
import java.util.*

data class Game(
    val id: Int,
    val name: String,
    val imageURL: String,
    val date: Date,
    val rating: Double
) : Serializable {
    override fun toString(): String {
        return "Game Title:\n$name\nRating:\n$rating\nRelease Date:\n" +
                "${SimpleDateFormat("dd MMM, YYYY").format(date)}\n"

    }
}
