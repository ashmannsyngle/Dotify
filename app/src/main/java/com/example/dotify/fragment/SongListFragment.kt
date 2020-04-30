package com.example.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.example.dotify.SongListAdapter
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {

    private var listOfSongs: List<Song>? = null
    private lateinit var songAdapter: SongListAdapter
    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        const val ARG_LIST_OF_SONGS = "arg_list_of_songs"
        val TAG: String = SongListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val list = args.getParcelableArrayList<Song>(ARG_LIST_OF_SONGS)
            if (list != null) {
                this.listOfSongs = list.toList()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOfSongs?.let {list ->
            this.songAdapter = SongListAdapter(list)
        }

        songAdapter.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }

        rvSongs.adapter = songAdapter
    }

    fun shuffleList(changedList: List<Song>) {
        songAdapter.change(changedList)
    }

    fun updateList(changedList: List<Song>) {
        listOfSongs = changedList
    }
}