package com.satryaway.bcgdvtest.feature.search

import com.satryaway.bcgdvtest.ApiRepository
import com.satryaway.bcgdvtest.ApiService
import com.satryaway.bcgdvtest.SongModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter {
    
    private var view: SearchView? = null

    private val apiRepository = ApiRepository()
    
    fun attachView(view: SearchView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun performSearch(text: String) {
        if (text.isEmpty()) {
            // show Toast
        } else {
            searchSong(text)
        }
    }

    private fun searchSong(text: String) {
        GlobalScope.launch {
            val songList = apiRepository.search(text)
            view?.handleSongSearchResult(songList.results)
        }
    }
}