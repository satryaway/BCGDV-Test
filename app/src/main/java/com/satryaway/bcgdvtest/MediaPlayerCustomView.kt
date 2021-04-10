package com.satryaway.bcgdvtest

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.bottom_player_view.view.*

class MediaPlayerCustomView : RelativeLayout, View.OnClickListener {
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    interface OnControlListener {
        fun onClickControl()
    }

    private var onControlListener: OnControlListener? = null
    private var songModel: SongModel? = null
    private var mediaPlayerPresenter: MediaPlayerPresenter? = null

    private var songNameTv: TextView? = null
    private var songAlbumTv: TextView? = null

    init {
        inflate(context, R.layout.bottom_player_view, this)
        songNameTv = findViewById(R.id.tv_selected_song_name)
        songAlbumTv = findViewById(R.id.tv_selected_album_name)
    }

    fun setControlListener(onControlListener: OnControlListener) {
        this.onControlListener = onControlListener
    }

    fun controlSong() {
        mediaPlayerPresenter?.let {
            if (it.isMediaPlaying()) {
                iv_control.setImageResource(R.drawable.ic_pause)
            } else {
                iv_control.setImageResource(R.drawable.ic_play)
            }
        }
    }

    fun setAttributes(songModel: SongModel) {
        this.songModel = songModel
        songNameTv?.text = songModel.trackName
        songAlbumTv?.text = songModel.collectionName
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_control) {
            onControlListener?.onClickControl()
        }
    }

    fun setPresenter(mediaPlayerPresenter: MediaPlayerPresenter) {
        if (this.mediaPlayerPresenter == null) {
            this.mediaPlayerPresenter = mediaPlayerPresenter
        }
    }
}