package com.satryaway.bcgdvtest

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.satryaway.bcgdvtest.adapter.SongListAdapter
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import com.satryaway.bcgdvtest.util.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_view.*
import kotlinx.android.synthetic.main.activity_main.v_media_player as mediaPlayerView

class MainActivity : AppCompatActivity(), SearchView, MediaPlayerView,
    MediaPlayerCustomView.OnControlListener {

    private val searchPresenter: SearchPresenter = SearchPresenter()
    private val mediaPlayerPresenter = MediaPlayerPresenter()

    private val listAdapter = SongListAdapter(this)
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

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

        mediaPlayerView.setControlListener(this)
    }

    private fun initAdapter() {
        recycler_view.adapter = listAdapter
        recycler_view.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun initRunnable() {
        runnable = Runnable {
            sb_duration.progress = mediaPlayerPresenter.getProgress()
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    override fun handleSongSearchResult(songList: ArrayList<SongModel>) {
        runOnUiThread {
            listAdapter.updateData(songList)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun playSong(songModel: SongModel) {
        mediaPlayerPresenter.playAudio(this, songModel)
    }

    override fun onPlayMediaPlayer(duration: Int, songModel: SongModel) {
        sb_duration.progress = 0
        sb_duration.max = duration
        mediaPlayerView.setAttributes(songModel)
        mediaPlayerView.visibility = View.VISIBLE
        mediaPlayerView.controlSong(true)

        initRunnable()
    }

    override fun onResumeMediaPlayer() {
        mediaPlayerView.controlSong(true)
        initRunnable()
    }

    override fun onPauseMediaPlayer(length: Int) {
        handler.removeCallbacks(runnable)
    }

    override fun onMediaCompletion() {
        mediaPlayerView.controlSong(false)
        handler.removeCallbacks(runnable)
    }

    override fun onClickControl() {
        mediaPlayerPresenter.controlPlayer()
        mediaPlayerView.controlSong(mediaPlayerPresenter.isMediaPlaying())
    }

    override fun onSeekControl(progress: Int) {
        mediaPlayerPresenter.setProgress(progress)
    }

    override fun onDestroy() {
        super.onDestroy()

        searchPresenter.detachView()
        mediaPlayerPresenter.detachView()
        mediaPlayerPresenter.destroyMediaPlayer()
    }
}