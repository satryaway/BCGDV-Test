package com.satryaway.bcgdvtest

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView

class MediaPlayerPresenter : MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    var mediaPlayer = MediaPlayer()

    var isMediaEnded: Boolean = false
    private var view: MediaPlayerView? = null
    private lateinit var songModel: SongModel

    fun attachView(view: MediaPlayerView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun playAudio(context: Context, songModel: SongModel) {
        this.songModel = songModel
        mediaPlayer.stop()
        mediaPlayer = MediaPlayer()

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
            setOnCompletionListener(this@MediaPlayerPresenter)
        }

        view?.onLoadingMedia()
    }

    fun isMediaPlaying(): Boolean = mediaPlayer.isPlaying

    fun getProgress() = mediaPlayer.currentPosition

    fun controlPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            view?.onPauseMediaPlayer(mediaPlayer.currentPosition)
        } else {
            if (isMediaEnded) {
                mediaPlayer.seekTo(0)
            } else {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
            }
            mediaPlayer.start()
            view?.onResumeMediaPlayer()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
        view?.onPlayMediaPlayer(mp?.duration ?: 0, songModel)
    }

    fun destroyMediaPlayer() {
        mediaPlayer.release()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        view?.onMediaCompletion()
    }

    fun setProgress(progress: Int) {
        mediaPlayer.seekTo(progress)
    }
}