package com.example.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.example.dotify.fragment.NowPlayingFragment
import com.example.dotify.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_ultimate_main.*
import java.util.*
import kotlin.collections.ArrayList

class UltimateMainActivity : AppCompatActivity(),
    OnSongClickListener {

    companion object {
        private var currSong: Song? = null
        private lateinit var listOfSongs: ArrayList<Song>
        private const val CURR_SONG = "curr_song"
        private const val SONG_LIST = "song_list"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ultimate_main)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val prevCurrSong = getParcelable<Song>(CURR_SONG)
                prevCurrSong?.let {
                    tvPreviewSongTitle.text = getString(R.string.previewText).format(it.title, it.artist)
                    currSong = prevCurrSong
                }

                if (containsKey(SONG_LIST)) {
                    Log.d("yes", "CONTAINS SONG LIST")
                    listOfSongs = getParcelableArrayList<Song>(SONG_LIST) as ArrayList<Song>
                    getSongListFragment()?.updateList(listOfSongs)
                }

            }

            if (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name == NowPlayingFragment.TAG) {
                clMiniPlayer.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } else {
            listOfSongs = ArrayList<Song>(SongDataProvider.getAllSongs())

            val songListFragment = SongListFragment()
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_LIST_OF_SONGS, listOfSongs)
            }
            songListFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .addToBackStack(SongListFragment.TAG)
                .commit()
        }

        btnShuffle.setOnClickListener {
            val shuffledSongs = listOfSongs.toList().shuffled(Random())
            getSongListFragment()?.shuffleList(shuffledSongs)
            listOfSongs = ArrayList<Song>(shuffledSongs)
        }

        clMiniPlayer.setOnClickListener {
            clMiniPlayer.visibility = View.GONE
            currSong?.let {
                showNowPlaying(it)
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (getNowPlayingFragment() != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        clMiniPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    override fun onBackPressed() {
        clMiniPlayer.visibility = View.VISIBLE

        super.onBackPressed()
    }

    override fun onSongClicked(song: Song) {
        tvPreviewSongTitle.text = getString(R.string.previewText).format(song.title, song.artist)
        currSong = song
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURR_SONG, currSong)
        outState.putParcelableArrayList(SONG_LIST, listOfSongs)

        super.onSaveInstanceState(outState)
    }

    private fun showNowPlaying(song: Song) {
        val nowPlayingFragment = getNowPlayingFragment()

        if (nowPlayingFragment == null) {
            val nowPlayingFragmentTemp = NowPlayingFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(NowPlayingFragment.ARG_SONG, song)
            }
            nowPlayingFragmentTemp.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragmentTemp, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()
        } else {
            nowPlayingFragment.updateSong(song)
        }
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment
    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

}
