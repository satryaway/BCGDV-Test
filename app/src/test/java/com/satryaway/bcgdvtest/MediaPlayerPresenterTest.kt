package com.satryaway.bcgdvtest

import android.media.MediaPlayer
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerPresenter
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.feature.search.SongModel
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class MediaPlayerPresenterTest {

    private var view: MediaPlayerView = mockk()
    private val presenter = spyk(MediaPlayerPresenter())
    private var mediaPlayer = mockk<MediaPlayer>(relaxed = true)

    @Before
    fun setup() {
        view.let { presenter.attachView(it) }
        every { presenter.mediaPlayer } returns mediaPlayer
    }

    @After
    fun destroy() {
        unmockkAll()
    }

    @Test
    fun `play media should be loading first`() {
        // Given
        val mockSongModel = SongModel("a", "b", "c", "d", "e")
        every { mediaPlayer.stop() } returns  Unit
        every { view.onLoadingMedia() } returns Unit

        // When
        presenter.playAudio(mockSongModel)

        // Then
        verify {
            view.onLoadingMedia()
        }
    }

    @Test
    fun `control player`() {
        // Given
        every {
            view.onResumeMediaPlayer()
        } returns Unit
        every {
            presenter.mediaPlayer.start()
        } returns Unit

        // When
        presenter.controlPlayer()

        // Then
        verify {
            view.onPauseMediaPlayer(any())
        }
    }
}