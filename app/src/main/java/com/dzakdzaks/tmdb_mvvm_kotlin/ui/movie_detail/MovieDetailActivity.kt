package com.dzakdzaks.tmdb_mvvm_kotlin.ui.movie_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import com.dzakdzaks.tmdb_mvvm_kotlin.di.Injection
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard.DashboardFragment
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.photo_view.PhotoPreviewActivity
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.MainViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_error.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var movieID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupActionBar()
        if (intent != null) {
            movieID = intent.getIntExtra("movieID", 0)
            setupViewModel()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setContentScrimColor(resources.getColor(R.color.colorWhite))
        collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.colorPrimary))
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.colorWhite))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainViewModel::class.java)
        movieID?.let { viewModel.initRepo().retrieveMovieDetail(it).observe(this, renderDetail) }
        viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)
        viewModel.initRepo().onErrorMessage().observe(this, onMessageErrorObserver)
    }

    @SuppressLint("SetTextI18n")
    private val renderDetail = Observer<ResponseMovieDetail> {
//        Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        collapsing_toolbar.title = it.title
        textTitle.text = it.title
        textOriginalTitle.text = it.originalTitle
        textReleaseDate.text = it.releaseDate?.let { it -> Utils.dateFormater(it) }
        textVoteAverage.text = "${it.voteAverage} / 10"
        textCountry.text = it.productionCountries!![0]?.name
        textOverview.text = "${it.overview} \n\n ${resources.getString(R.string.large_text)}"

        val imgBGUrl = BuildConfig.IMAGE_URL_BACKDROP + it.backdropPath
        val imgPosterUrl = BuildConfig.IMAGE_URL_POSTER + it.posterPath

        Glide.with(imgThumb.context)
            .load(imgBGUrl)
            .into(imgThumb)

        Glide.with(imgPoster.context)
            .load(imgPosterUrl)
            .into(imgPoster)

        imgThumb.setOnClickListener {
            val i = Intent(this, PhotoPreviewActivity::class.java)
            i.putExtra("url", imgBGUrl)
            startActivity(i)
        }

        imgPoster.setOnClickListener {
            val i = Intent(this, PhotoPreviewActivity::class.java)
            i.putExtra("url", imgPosterUrl)
            startActivity(i)
        }


    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(DashboardFragment.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        layoutErrorDetailMovie.visibility = View.GONE
        progress_bar.visibility = visibility
    }

    @SuppressLint("SetTextI18n")
    private val onMessageErrorObserver = Observer<Any> {
        Log.v(DashboardFragment.TAG, "onMessageError $it")
        layoutErrorDetailMovie.visibility = View.VISIBLE
        textViewError.text = "Error ($it)"
    }

    override fun onResume() {
        super.onResume()
        movieID?.let { viewModel.initRepo().retrieveMovieDetail(it) }
    }
}
