package com.satryaway.bcgdvtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satryaway.bcgdvtest.R
import com.satryaway.bcgdvtest.feature.search.SongModel
import com.satryaway.bcgdvtest.feature.mediaplayer.MediaPlayerView
import com.satryaway.bcgdvtest.util.ImageUtils
import java.util.ArrayList

class SongListAdapter(private var mediaPlayerView: MediaPlayerView) :
    RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    var songList = arrayListOf<SongModel>()
    var selectedPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivSongCover: ImageView = view.findViewById(R.id.iv_song_cover)
        val tvSongName: TextView = view.findViewById(R.id.tv_song_name)
        val tvArtistName: TextView = view.findViewById(R.id.tv_artist_name)
        val tvAlbumName: TextView = view.findViewById(R.id.tv_album_name)
        val rlWrapper: RelativeLayout = view.findViewById(R.id.rl_wrapper)
        val indicator: ImageView = view.findViewById(R.id.iv_playing_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSongName.text = songList[position].trackName
        holder.tvAlbumName.text = songList[position].collectionName
        holder.tvArtistName.text = songList[position].artistName
        ImageUtils.loadImage(songList[position].artworkUrl100, holder.ivSongCover)
        holder.indicator.visibility = if (selectedPosition == position) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        holder.rlWrapper.setOnClickListener {
            holder.indicator.visibility = View.VISIBLE
            mediaPlayerView.playSong(songList[position])
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    fun updateData(songList: ArrayList<SongModel>) {
        this.songList = songList
        this.selectedPosition = -1
        notifyDataSetChanged()
    }
}