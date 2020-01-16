package mobileapps.mobile_kotlin

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_video_game.view.*

class MainAdapter(private val gameList: Array<Game>) : RecyclerView.Adapter<VideoGameViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.fragment_video_game, parent, false)
        return VideoGameViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: VideoGameViewHolder, position: Int) {
        val currentGame = gameList[position]

        holder.view.textView_game_title.text = currentGame.name
        holder.view.ratingBar.rating = currentGame.rating.toFloat()
        holder.view.textView_date.text = SimpleDateFormat("dd MMM, YYYY").format(currentGame.date)
        val imageURL =
            if (currentGame.imageURL.isNotEmpty()) currentGame.imageURL
            else "https://aprov.ro/wp-content/themes/brixel/images/No-Image-Found-400x264.png"

        Picasso.get()
            .load(imageURL)
            .placeholder(R.drawable.progress_animation)
            .resize(1280, 720)
            .onlyScaleDown()
            .centerCrop()
            .into(holder.view.imageView)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

}

class VideoGameViewHolder(val view: View) : RecyclerView.ViewHolder(view)