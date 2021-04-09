package com.satryaway.bcgdvtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView {

    private val searchPresenter: SearchPresenter = SearchPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchPresenter.attachView(this)
    }
}