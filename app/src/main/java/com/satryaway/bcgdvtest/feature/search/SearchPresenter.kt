package com.satryaway.bcgdvtest.feature.search

class SearchPresenter {
    
    private var view: SearchView? = null
    
    fun attachView(view: SearchView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}