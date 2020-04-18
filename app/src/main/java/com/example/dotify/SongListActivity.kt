package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.random.Random

class SongListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        val listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs, this)

        songAdapter.onSongClickListener = { song ->
            tvPreviewSongTitle.text = getString(R.string.previewText).format(song.title, song.artist)
            clMiniPlayer.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(SONG_KEY, song)
                startActivity(intent)
            }
        }

        btnShuffle.setOnClickListener{
            songAdapter.change(listOfSongs.shuffled(Random))
        }

        rvSongs.adapter = songAdapter
    }
}
