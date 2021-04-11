package com.satryaway.bcgdvtest.feature.search

interface SearchView {
    fun handleSongSearchResult(songList: ArrayList<SongModel>)
    fun showErrorSearch()
    fun showEmptyView()
    fun validateSearch()
}