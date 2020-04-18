package com.example.dotify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private var listOfSongs: List<Song>, context: Context): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: ((song : Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    fun change(newSongs : List<Song>) {
        listOfSongs = newSongs
        notifyDataSetChanged()
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongTitle by lazy { itemView.findViewById<TextView>(R.id.tvSongTitle) }
        private val tvArtistName by lazy { itemView.findViewById<TextView>(R.id.tvArtistName) }
        private val ivSongImage by lazy { itemView.findViewById<ImageView>(R.id.songImage) }

        fun bind(song: Song) {
            tvSongTitle.text = song.title
            tvArtistName.text = song.artist
            ivSongImage.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}