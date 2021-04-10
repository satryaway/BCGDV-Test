package com.satryaway.bcgdvtest.feature.mediaplayer

import com.satryaway.bcgdvtest.SongModel

interface MediaPlayerView {
    fun playSong(songModel: SongModel)
    fun setMediaPlayerLoading()
    fun setSongAttributes(songModel: SongModel)
    fun showMediaPlayerView()
    fun onMediaCompletion()
}