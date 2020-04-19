package com.example.dotify

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(10000, 100000)

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        if (song != null) {
            song_name.text = song.title
            cover.setImageResource(song.largeImageID)
            artist_name.text = song.artist
        }

        val noOfPlays = findViewById<TextView>(R.id.no_of_plays)
        val text = "$randomNumber plays"
        noOfPlays.text = text

        val playButton = findViewById<ImageButton>(R.id.media_play)
        playButton.setOnClickListener {
            randomNumber++
            val text = "$randomNumber plays"
            noOfPlays.text = text
        }

        val prevButton = findViewById<ImageButton>(R.id.media_previous)
        prevButton.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        val nextButton = findViewById<ImageButton>(R.id.media_next)
        nextButton.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        val image = findViewById<ImageButton>(R.id.cover)
        image.setOnLongClickListener {
            val color = ContextCompat.getColor(this, R.color.red)
            val username = findViewById<TextView>(R.id.username)
            username.setTextColor(color)
            val songName = findViewById<TextView>(R.id.song_name)
            songName.setTextColor(color)
            val artistName = findViewById<TextView>(R.id.artist_name)
            artistName.setTextColor(color)
            noOfPlays.setTextColor(color)
            true
        }
    }
}
