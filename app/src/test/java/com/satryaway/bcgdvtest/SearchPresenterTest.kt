package com.satryaway.bcgdvtest

import com.satryaway.bcgdvtest.api.ApiRepository
import com.satryaway.bcgdvtest.api.ApiService
import com.satryaway.bcgdvtest.feature.search.ResultSongModel
import com.satryaway.bcgdvtest.feature.search.SearchPresenter
import com.satryaway.bcgdvtest.feature.search.SearchView
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class SearchPresenterTest {
    private var view: SearchView = mockk()
    private val searchPresenter = spyk(SearchPresenter())
    private var apiMock = mockk<ApiService>()
    private val deferredMock = mockk<Call<ResultSongModel>>()
    private val mockRepo = mockk<ApiRepository>()

    @Before
    fun setup() {
        view.let { searchPresenter.attachView(it) }
        mockkStatic("retrofit2.KotlinExtensions")
    }

    @After
    fun destroy() {
        unmockkAll()
    }

    @Test
    fun `a correct keyword should return songs list`() {
        // Given
        val resultSongModel = ResultSongModel(2, arrayListOf())
        coEvery { apiMock.searchSong(any()) } returns deferredMock
        coEvery { mockRepo.search(any()) } returns resultSongModel

        every { searchPresenter.apiRepository } returns mockRepo
        every { view.handleSongSearchResult(any()) } returns Unit

        // When
        searchPresenter.performSearch("")

        // Then
        verify {
            view.handleSongSearchResult(resultSongModel.results)
        }
    }

    @Test
    fun `zero search result will show empty view`() {
        // Given
        val resultSongModel = ResultSongModel(0, arrayListOf())
        coEvery { apiMock.searchSong(any()) } returns deferredMock
        coEvery { mockRepo.search(any()) } returns resultSongModel

        every { searchPresenter.apiRepository } returns mockRepo
        every { view.showEmptyView() } returns Unit


        // When
        searchPresenter.performSearch("")

        // Then
        verify {
            view.showEmptyView()
        }
    }
}