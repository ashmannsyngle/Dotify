package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(10000, 100000)

    companion object {
        const val SONG_KEY = "SONG_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        intent.extras?.let {nonNullExtra ->
//            if (nonNullExtra.containsKey(SONG_KEY)) {
//
//
//            }
//        }
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
        playButton.setOnClickListener {playButton: View ->
            randomNumber++
            val text = "$randomNumber plays"
            noOfPlays.text = text
        }

        val prevButton = findViewById<ImageButton>(R.id.media_previous)
        prevButton.setOnClickListener {prevButton: View ->
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        val nextButton = findViewById<ImageButton>(R.id.media_next)
        nextButton.setOnClickListener {nextButton: View ->
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        val image = findViewById<ImageButton>(R.id.cover)
        image.setOnLongClickListener {image: View ->
            val color = ContextCompat.getColor(this, R.color.red)
            val username = findViewById<TextView>(R.id.username)
            username.setTextColor(color)
            val songName = findViewById<TextView>(R.id.song_name)
            songName.setTextColor(color)
            val artistName = findViewById<TextView>(R.id.artist_name)
            artistName.setTextColor(color)
            val plays = findViewById<TextView>(R.id.no_of_plays)
            plays.setTextColor(color)
            true
        }
    }

    fun changeUserClicked(view: View) {
        val changeUsernameButton = findViewById<Button>(R.id.btn_change_user)
        val username = findViewById<TextView>(R.id.username)
        val editUsername = findViewById<EditText>(R.id.edit_username)
        if (changeUsernameButton.text != "Apply") {
            username.visibility = View.GONE
            editUsername.visibility = View.VISIBLE
            changeUsernameButton.text = "Apply"
        } else {
            username.text = editUsername.text.toString()
            if (username.text == "") {
                Toast.makeText(this, "Cannot apply if username is empty!", Toast.LENGTH_SHORT).show()
            } else {
                editUsername.visibility = View.GONE
                username.visibility = View.VISIBLE
                changeUsernameButton.text = "Change User"
            }
        }
    }
}
