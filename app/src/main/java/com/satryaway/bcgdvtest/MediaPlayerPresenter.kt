package com.satryaway.bcgdvtest

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SearchView

class MediaPlayerPresenter : MediaPlayer.OnPreparedListener {
    var mediaPlayer = MediaPlayer()

    private var view: MediaPlayerView? = null
    private lateinit var songModel: SongModel
    private var length = 0

    fun attachView(view: MediaPlayerView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun playAudio(context: Context, songModel: SongModel) {
        this.songModel = songModel
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
        }

        mediaPlayer.apply {
            setOnPreparedListener(this@MediaPlayerPresenter)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(songModel.previewUrl)
            prepareAsync()
        }

        view?.setMediaPlayerLoading()
    }

    fun isMediaPlaying(): Boolean = mediaPlayer.isPlaying

    fun controlPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            length = mediaPlayer.currentPosition
        } else {
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
        view?.showMediaPlayerView()
        view?.setSongAttributes(songModel)
    }

    fun destroyMediaPlayer() {
        mediaPlayer.release()
    }
}