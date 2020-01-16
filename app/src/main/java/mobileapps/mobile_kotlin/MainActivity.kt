package mobileapps.mobile_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(emptyArray())


        floatingActionButton.setOnClickListener {
            val intent = Intent(recyclerView_main.context, VideoGameActivity::class.java)
            recyclerView_main.context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchGames()
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
                    val gameList = gson.fromJson(body, Array<Game>::class.java)

                    runOnUiThread {
                        recyclerView_main.adapter = MainAdapter(gameList)
                    }
                }
                else -> println("Server declined request")
            }
        })
    }
}
