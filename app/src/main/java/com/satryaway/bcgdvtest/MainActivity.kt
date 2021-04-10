package com.satryaway.bcgdvtest

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.satryaway.bcgdvtest.adapter.SongListAdapter
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import com.satryaway.bcgdvtest.util.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView, MediaPlayerView,
    MediaPlayerCustomView.OnControlListener {

    private val searchPresenter: SearchPresenter = SearchPresenter()

    private val listAdapter = SongListAdapter(this)
    private val mediaPlayerPresenter = MediaPlayerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchPresenter.attachView(this)
        mediaPlayerPresenter.attachView(this)

        initAdapter()
        initView()
    }

    private fun initView() {
        btn_search.setOnClickListener {
            val text = et_input_keyword.text.toString()
            CommonUtils.hideSoftKeyboard(this, it)
            searchPresenter.performSearch(text)
        }

        v_media_player.setControlListener(this)
        v_media_player.setOnClickListener {
            mediaPlayerPresenter.controlPlayer()
            v_media_player.controlSong()
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
        v_media_player.setPresenter(mediaPlayerPresenter)
        mediaPlayerPresenter.playAudio(this, songModel)
    }

    override fun setMediaPlayerLoading() {
    }

    override fun setSongAttributes(songModel: SongModel) {
        v_media_player.setAttributes(songModel)
    }

    override fun showMediaPlayerView() {
        v_media_player.visibility = View.VISIBLE
        v_media_player.controlSong()
    }

    override fun onClickControl() {
        mediaPlayerPresenter.controlPlayer()
        v_media_player.controlSong()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.detachView()
        mediaPlayerPresenter.detachView()
        mediaPlayerPresenter.destroyMediaPlayer()
    }
}