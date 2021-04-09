package com.satryaway.bcgdvtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satryaway.bcgdvtest.R
import com.satryaway.bcgdvtest.SongModel
import com.satryaway.bcgdvtest.util.ImageUtils
import java.util.ArrayList

class SongListAdapter : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    var songList = arrayListOf<SongModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivSongCover: ImageView = view.findViewById(R.id.iv_song_cover)
        val tvSongName: TextView = view.findViewById(R.id.tv_song_name)
        val tvArtistName: TextView = view.findViewById(R.id.tv_artist_name)
        val tvAlbumName: TextView = view.findViewById(R.id.tv_album_name)
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
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    fun updateData(songList: ArrayList<SongModel>) {
        this.songList = songList
        notifyDataSetChanged()
    }
}