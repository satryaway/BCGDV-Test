package com.satryaway.bcgdvtest

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.satryaway.bcgdvtest.adapter.SongListAdapter
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView, MediaPlayerView {

    private val searchPresenter: SearchPresenter = SearchPresenter()

    private val listAdapter = SongListAdapter(this)
    private val audioManager = MediaPlayerPresenter()

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
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
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
    override fun playSong(songModel: SongModel) {
        audioManager.playAudio(this, songModel.previewUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.detachView()
        audioManager.destroyMediaPlayer()
    }
}