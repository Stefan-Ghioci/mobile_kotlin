package mobileapps.mobile_kotlin

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

        fetchGames()
    }

    private fun fetchGames() {
        println("Attempting to Fetch JSON")
        val url = "http://192.168.42.47:8080/mobileapps/games"
        val request = Request.Builder().url(url).header("Accept", "application/json").build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute fetch request: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                println("Fetch request executed successfully")

                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val gameList = gson.fromJson(body, Array<Game>::class.java)

//            gameList.forEach { println(it) }
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(gameList)
                }
            }
        })
    }
}
