package mobileapps.mobile_kotlin

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;
import kotlinx.android.synthetic.main.movie_row.view.*

class MainAdapter(val gameList: Array<Game>) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.movie_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentGame = gameList[position]
        holder.view.textView_game_title.text = currentGame.name
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)