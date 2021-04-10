package com.satryaway.bcgdvtest.feature.mediaplayer

import com.satryaway.bcgdvtest.SongModel

interface MediaPlayerView {
    fun playSong(songModel: SongModel)
}