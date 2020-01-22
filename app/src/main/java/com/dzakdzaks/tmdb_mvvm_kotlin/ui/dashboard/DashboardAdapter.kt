package com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.NowPlayingMovie
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:31.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard
 * ==================================//==================================
 * ==================================//==================================
 */

class DashboardAdapter(private var movies: List<NowPlayingMovie>) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var onItemClick: ((NowPlayingMovie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = movies[position].title
        holder.tvOverview.text = movies[position].overview
        holder.tvDate.text = movies[position].releaseDate?.let { Utils.dateFormater(it) }
        holder.tvVote.text = "${movies[position].voteAverage.toString()} / 10"

        val imgPosterUrl = BuildConfig.IMAGE_URL_POSTER + movies[position].posterPath
        val imgBGUrl = BuildConfig.IMAGE_URL_BACKDROP + movies[position].backdropPath

        Glide.with(holder.imgPoster.context)
            .load(imgPosterUrl)
            .into(holder.imgPoster)

        Glide.with(holder.imgPoster.context)
            .load(imgBGUrl)
            .into(holder.imgBg)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movies[position])
        }
    }

    fun updateData(data: List<NowPlayingMovie>) {
        movies = data
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.textMovieTitle)
        var imgPoster: ImageView = itemView.findViewById(R.id.imageMovie)
        var imgBg: ImageView = itemView.findViewById(R.id.imageMovieBg)
        var tvOverview: TextView = itemView.findViewById(R.id.textMovieOverview)
        var tvDate: TextView = itemView.findViewById(R.id.textMovieReleaseDate)
        var tvVote: TextView = itemView.findViewById(R.id.textMovieVoteAverage)
    }
}