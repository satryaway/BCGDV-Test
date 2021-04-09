package com.satryaway.bcgdvtest

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi

class AudioManager: MediaPlayer.OnPreparedListener {
    var mediaPlayer = MediaPlayer()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun playAudio(context: Context, url: String) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
        }

        mediaPlayer.apply {
            setOnPreparedListener(this@AudioManager)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

    fun destroyMediaPlayer() {
        mediaPlayer.release()
    }
}