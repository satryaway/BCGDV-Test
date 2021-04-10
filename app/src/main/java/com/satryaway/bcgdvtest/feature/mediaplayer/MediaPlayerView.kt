package com.satryaway.bcgdvtest.feature.mediaplayer

import com.satryaway.bcgdvtest.SongModel

interface MediaPlayerView {
    fun playSong(songModel: SongModel)
    fun onMediaCompletion()
    fun onPauseMediaPlayer(length: Int)
    fun onPlayMediaPlayer(duration: Int, songModel: SongModel)
    fun onResumeMediaPlayer()
}