package com.satryaway.bcgdvtest

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.player_view.view.*

class MediaPlayerCustomView : RelativeLayout, View.OnClickListener,
    SeekBar.OnSeekBarChangeListener {
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
        fun onSeekControl(progress: Int)
    }

    private var onControlListener: OnControlListener? = null
    private var songModel: SongModel? = null

    private var songNameTv: TextView? = null
    private var songAlbumTv: TextView? = null
    private var controlIv: ImageView? = null

    init {
        inflate(context, R.layout.player_view, this)
        songNameTv = findViewById(R.id.tv_selected_song_name)
        songAlbumTv = findViewById(R.id.tv_selected_album_name)
        controlIv = findViewById(R.id.iv_control)

        controlIv?.setOnClickListener(this)
    }

    fun setControlListener(onControlListener: OnControlListener) {
        this.onControlListener = onControlListener
    }

    fun controlSong(isMediaPlaying: Boolean) {
        if (isMediaPlaying) {
            iv_control.setImageResource(R.drawable.ic_pause)
        } else {
            iv_control.setImageResource(R.drawable.ic_play)
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

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            onControlListener?.onSeekControl(progress * 1000)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}