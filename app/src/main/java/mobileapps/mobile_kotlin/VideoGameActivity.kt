package mobileapps.mobile_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_add_game.*
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class VideoGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)


        form {
            input(R.id.editText_name) {
                isNotEmpty()
                    .description("Game Title must not be empty")
            }
            input(R.id.editText_image_url) {
                matches("(^(?![\\s\\S]))|((http(s?):)([\\/|.|\\w|\\s|-])*\\.(?:jpg|gif|png|jpeg))")
                    .description("URL is not valid \n (you can leave it empty)")

            }
            input(R.id.editText_date) {
                isNotEmpty()
                    .description("Release Date must not be empty")
                matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
                    .description("Date must be in yyyy-mm-dd format")
            }

            submitWith(R.id.button_add) { postGame() }
        }
    }

    data class GameDTO(
        val name: String,
        val imageURL: String,
        val date: String,
        val rating: Double
    )

    private fun postGame() {

        val gson = GsonBuilder().create()

        val name = editText_name.text.toString()
        val imageURL = editText_image_url.text.toString()
        val date = editText_date.text.toString()
        val rating = ratingBar.rating.toDouble()

        val game = GameDTO(name, imageURL, date, rating)
        val json = gson.toJson(game)

        println("Attempting to post game $json")

        val url = "http://192.168.56.1:8080/mobileapps/add-game"
        val request = Request.Builder()
            .url(url)
            .header("content-type", "application/json")
            .method("POST", json.toRequestBody())
            .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute post request: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) = when {
                response.isSuccessful -> {
                    println("Game added with success")

                    val intent = Intent(linearLayout_add_game.context, MainActivity::class.java)
                    linearLayout_add_game.context.startActivity(intent)
                }
                else -> {
                    println("Failed to add game, server declined request")
                }
            }
        })
    }
}