package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        val songTitle = song.title
        val artistName = song.artist
        val songImage = song.smallImageID
        holder.bind(songTitle, artistName, songImage)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongTitle by lazy { itemView.findViewById<TextView>(R.id.tvSongTitle) }
        private val tvArtistName by lazy { itemView.findViewById<TextView>(R.id.tvArtistName) }
        private val ivSongImage by lazy { itemView.findViewById<ImageView>(R.id.songImage) }

        fun bind(songTitle: String, artistName: String, songImage: Int) {
            tvSongTitle.text = songTitle
            tvArtistName.text = artistName
            ivSongImage.setImageResource(songImage)
        }
    }
}