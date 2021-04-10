package com.satryaway.bcgdvtest.feature.mediaplayer

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.satryaway.bcgdvtest.R
import com.satryaway.bcgdvtest.feature.search.SongModel
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

    init {
        inflate(context, R.layout.player_view, this)

        iv_control.setOnClickListener(this)
        sb_duration.setOnSeekBarChangeListener(this)
        rl_parent_wrapper.setOnClickListener(this)
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
        tv_selected_song_name.text = songModel.trackName
        tv_selected_album_name.text = songModel.collectionName
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_control -> {
                onControlListener?.onClickControl()
            }
            R.id.rl_parent_wrapper -> {}
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            onControlListener?.onSeekControl(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}