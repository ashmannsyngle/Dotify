package com.example.dotify.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {

    private var song: Song? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
        const val PLAY_COUNT = "play_count"
        var randomNumber: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            this.song = args.getParcelable(ARG_SONG)
        }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                if (containsKey(PLAY_COUNT)) {
                    randomNumber = getInt(PLAY_COUNT)
                    val text = "$randomNumber plays"
                    if (no_of_plays != null) {
                        no_of_plays.text = text
                    }
                }
            }
        } else {
            randomNumber = Random.nextInt(10000, 100000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        randomNumber?.let {
            outState.putInt(PLAY_COUNT, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        song?.let {s ->
            song_name.text = s.title
            cover.setImageResource(s.largeImageID)
            artist_name.text = s.artist
        }

        val text = "$randomNumber plays"
        no_of_plays.text = text

        media_play.setOnClickListener {
            randomNumber?.let {rand ->
                randomNumber = rand + 1
            }
            val text = "$randomNumber plays"
            no_of_plays.text = text
        }

        media_previous.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        media_next.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSong(song: Song) {
        song_name.text = song.title
        cover.setImageResource(song.largeImageID)
        artist_name.text = song.artist
    }
}