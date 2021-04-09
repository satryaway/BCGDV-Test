package com.satryaway.bcgdvtest

import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.satryaway.bcgdvtest.adapter.SongListAdapter
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView {

    private val searchPresenter: SearchPresenter = SearchPresenter()

    private val listAdapter = SongListAdapter(this)
    private val audioManager = AudioManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchPresenter.attachView(this)

        initAdapter()
        initView()
    }

    private fun initView() {
        btn_search.setOnClickListener {
            val text = et_input_keyword.text.toString()
            searchPresenter.performSearch(text)
        }
    }

    private fun initAdapter() {
        recycler_view.adapter = listAdapter
        recycler_view.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun handleSongSearchResult(songList: ArrayList<SongModel>) {
        runOnUiThread {
            listAdapter.updateData(songList)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun playSong(songUrl: String) {
        audioManager.playAudio(this, songUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        audioManager.destroyMediaPlayer()
    }
}