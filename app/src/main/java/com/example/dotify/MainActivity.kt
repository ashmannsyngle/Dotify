package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(10000, 100000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editUsername = findViewById<TextView>(R.id.edit_username)
        editUsername.visibility = View.GONE

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
    }

    fun changeUserClicked(view: View) {
        val changeUsernameButton = findViewById<Button>(R.id.btn_change_user)
        val username = findViewById<TextView>(R.id.username)
        val editUsername = findViewById<TextView>(R.id.edit_username)
        if (changeUsernameButton.text != "Apply") {
            username.visibility = View.GONE
            editUsername.visibility = View.VISIBLE
            changeUsernameButton.text = "Apply"
        } else {
            username.text = editUsername.text.toString()
            editUsername.visibility = View.GONE
            username.visibility = View.VISIBLE
            changeUsernameButton.text = "Change User"
        }
    }
}
