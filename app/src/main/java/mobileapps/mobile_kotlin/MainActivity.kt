package mobileapps.mobile_kotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : BaseActivity() {

    private lateinit var gameList: Array<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        gameList = emptyArray()
        recyclerView_main.adapter = MainAdapter(gameList)


        floatingActionButton.setOnClickListener {
            val intent = Intent(recyclerView_main.context, VideoGameActivity::class.java)
            recyclerView_main.context.startActivity(intent)
        }

        button_email.setOnClickListener {
            val subject = "Mobile App: Video Games List"
            var message = ""
            gameList.forEach { game ->
                message += "$game\n\n"
            }

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Send Email using:"))
        }
        fetchGames()

        val errorFromAdd = intent.getIntExtra("error", -1)
        println(errorFromAdd)
        showSnackBar(errorFromAdd)
    }

    private fun showStats(): View.OnClickListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun showSnackBar(errorFromAdd: Int) {
        if (errorFromAdd == 0) {
            fetchGames()
            val snack = Snackbar.make(
                recyclerView_main,
                "Game added successfully",
                Snackbar.LENGTH_SHORT
            )
            snack.view.setBackgroundColor(Color.parseColor("#388E3C"))
            snack.show()

        } else if (errorFromAdd == 1) {
            val snack = Snackbar.make(
                recyclerView_main,
                "A server error occurred whilst adding the game :(",
                Snackbar.LENGTH_LONG
            )
            snack.view.setBackgroundColor(Color.parseColor("#D32F2F"))
            snack.show()
        }
    }

    private fun fetchGames() {
        println("Attempting to Fetch JSON")
        val url = "http://192.168.56.1:8080/mobileapps/games"
        val request = Request.Builder().url(url).header("Accept", "application/json").build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute fetch request: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) = when {
                response.isSuccessful -> {
                    println("Game List successfully received from server")

                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    gameList = gson.fromJson(body, Array<Game>::class.java)

                    runOnUiThread {
                        recyclerView_main.adapter = MainAdapter(gameList)
                    }
                }
                else -> println("Server declined request")
            }
        })
    }
}
