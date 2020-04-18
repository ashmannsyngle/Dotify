package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.random.Random

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        val listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs, this)

        songAdapter.onSongClickListener = { song ->
            tvPreviewSongTitle.text = getString(R.string.previewText).format(song.title, song.artist)
        }

        btnShuffle.setOnClickListener{
            songAdapter.change(listOfSongs.shuffled(Random))
        }

        rvSongs.adapter = songAdapter
    }
}
