package mobileapps.mobile_kotlin

import java.util.*

data class Game(
    val id: Int,
    val name: String,
    val imageURL: String,
    val date: Date,
    val rating: Double
)
