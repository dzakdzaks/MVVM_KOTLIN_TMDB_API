package com.dzakdzaks.tmdb_mvvm_kotlin.ui.notifications

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.callback.OnclickAdapter
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:31.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard
 * ==================================//==================================
 * ==================================//==================================
 */

class NotificationAdapter(private var movies: List<Book>, private var onClickAdapter: OnclickAdapter) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    companion object {
        var mClickListener: OnclickAdapter? = null
    }

//    var onItemClick: ((Book) -> Unit)? = null
//    var onLongItemClick: ((Book) -> Unit)? = null

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
        mClickListener = onClickAdapter
        holder.tvTitle.text = movies[position].name
        holder.tvOverview.text = movies[position].author
        holder.tvDate.text = movies[position].createdAt
        holder.tvVote.text = movies[position].updatedAt

        val imgPosterUrl = BuildConfig.SERVER_HOST +  movies[position].image
        val imgBGUrl = BuildConfig.SERVER_HOST + movies[position].image

        Glide.with(holder.imgPoster.context)
            .load(imgPosterUrl)
            .into(holder.imgPoster)

        Glide.with(holder.imgPoster.context)
            .load(imgBGUrl)
            .into(holder.imgBg)

        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(movies[position])
            mClickListener!!.onItemClick(movies[position])
        }

        holder.itemView.setOnLongClickListener {
//            onLongItemClick?.invoke(movies[position])
            mClickListener!!.onItemLongClick(movies[position], holder.itemView)
            true
        }
    }

    fun updateData(data: List<Book>) {
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