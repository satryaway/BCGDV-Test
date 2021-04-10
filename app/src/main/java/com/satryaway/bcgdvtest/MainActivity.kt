package com.satryaway.bcgdvtest

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.satryaway.bcgdvtest.adapter.SongListAdapter
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_view.*
import java.util.*
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
        mediaPlayerView.setControlListener(this)
        et_input_keyword.addTextChangedListener(object: TextWatcher {
            var timer = Timer()
            val delay = 500L

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object: TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            pb_search_loading.visibility = View.VISIBLE
                            val text = et_input_keyword.text.toString()

                            if (text.isNotEmpty()) {
                                searchPresenter.performSearch(text)
                            }
                        }
                    }
                }, delay)
            }

        })
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
            pb_search_loading.visibility = View.INVISIBLE
            recycler_view.visibility = View.VISIBLE
            tv_empty_view.visibility = View.INVISIBLE
            recycler_view.scrollToPosition(0)
            listAdapter.updateData(songList)
        }
    }

    override fun showErrorSearch() {
        runOnUiThread {
            pb_search_loading.visibility = View.INVISIBLE
            Toast.makeText(
                this,
                getString(R.string.please_input_keyword), Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun showEmptyView() {
        runOnUiThread {
            pb_search_loading.visibility = View.INVISIBLE
            recycler_view.visibility = View.INVISIBLE
            tv_empty_view.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun playSong(songModel: SongModel) {
        mediaPlayerView.visibility = View.VISIBLE
        mediaPlayerPresenter.playAudio(this, songModel)
    }

    override fun onLoadingMedia() {
        pb_loading.visibility = View.VISIBLE
    }

    override fun onPlayMediaPlayer(duration: Int, songModel: SongModel) {
        pb_loading.visibility = View.INVISIBLE
        sb_duration.progress = 0
        sb_duration.max = duration
        mediaPlayerView.setAttributes(songModel)
        mediaPlayerView.controlSong(true)
        mediaPlayerPresenter.isMediaEnded = false

        initRunnable()
    }

    override fun onResumeMediaPlayer() {
        mediaPlayerView.controlSong(true)
        mediaPlayerPresenter.isMediaEnded = false
        initRunnable()
    }

    override fun onPauseMediaPlayer(length: Int) {
        mediaPlayerPresenter.isMediaEnded = false
        handler.removeCallbacks(runnable)
    }

    override fun onMediaCompletion() {
        mediaPlayerView.controlSong(false)
        mediaPlayerPresenter.isMediaEnded = true
        sb_duration.progress = 0
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