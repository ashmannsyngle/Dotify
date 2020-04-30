package com.example.dotify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs: List<Song> = listOfSongs.toList()
    var onSongClickListener: ((song : Song) -> Unit)? = null
    var onSongLongClickListener: ((song : Song) -> Unit)? = null

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
        val callback = SongDiffCallback(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
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

            itemView.setOnLongClickListener {
                onSongLongClickListener?.invoke(song)
                true
            }

        }
    }
}