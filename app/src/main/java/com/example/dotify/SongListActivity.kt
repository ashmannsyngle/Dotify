package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlin.random.Random

class SongListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        var listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs, this)

        songAdapter.onSongClickListener = { song ->
            tvPreviewSongTitle.text = getString(R.string.previewText).format(song.title, song.artist)
            clMiniPlayer.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(SONG_KEY, song)
                startActivity(intent)
            }
        }

        songAdapter.onSongLongClickListener = { song ->
            Toast.makeText(this, getString(R.string.deleteSongText).format(song.title), Toast.LENGTH_SHORT).show()
            val mutableListOfSongs = listOfSongs.toMutableList()
            songAdapter.change(mutableListOfSongs.apply {
                remove(song)
            })
            listOfSongs = mutableListOfSongs
            tvPreviewSongTitle.text = ""
        }

        btnShuffle.setOnClickListener{
            songAdapter.change(listOfSongs.shuffled(Random))
        }

        rvSongs.adapter = songAdapter
    }
}
