package com.satryaway.bcgdvtest.feature.search

import com.satryaway.bcgdvtest.SongModel

interface SearchView {
    fun handleSongSearchResult(songList: ArrayList<SongModel>)
}