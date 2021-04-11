package com.satryaway.bcgdvtest.feature.search

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.satryaway.bcgdvtest.api.ApiRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class SearchPresenter {

    private var view: SearchView? = null

    val apiRepository = ApiRepository()

    fun attachView(view: SearchView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun performSearch(text: String) {
        GlobalScope.launch {
            val songList = apiRepository.search(text)
            if (songList.resultCount > 0) {
                view?.handleSongSearchResult(songList.results)
            } else {
                view?.showEmptyView()
            }
        }
    }

    fun setSearchPerform(searchEt: EditText, activity: Activity) {
        searchEt.addTextChangedListener(object: TextWatcher {
            var timer = Timer()
            val delay = 500L

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object: TimerTask() {
                    override fun run() {
                        view?.validateSearch()
                    }
                }, delay)
            }
        })
    }
}