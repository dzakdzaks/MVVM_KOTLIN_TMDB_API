package com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie.NowPlayingMovie
import com.dzakdzaks.tmdb_mvvm_kotlin.di.Injection
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.movie_detail.MovieDetailActivity
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.MainViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_error.*

class DashboardFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: DashboardAdapter

    private var broadcastReceiver: BroadcastReceiver? = null

    companion object {
        const val TAG = "CONSOLE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter =
            DashboardAdapter(viewModel.initRepo().retrieveNowPlayingMovies().value ?: emptyList())

//        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        recyclerView.layoutManager = LinearLayoutManager(context)
//        else
//            recyclerView.layoutManager = GridLayoutManager(this, 4)

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        swipeDashboard.setOnRefreshListener {
            viewModel.initRepo().retrieveNowPlayingMovies().observe(this, renderMovies)
        }
        swipeDashboard.setColorSchemeColors(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimaryDark),
            resources.getColor(android.R.color.darker_gray)
        )
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainViewModel::class.java)
        viewModel.initRepo().retrieveNowPlayingMovies().observe(this, renderMovies)

        viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)
        viewModel.initRepo().onErrorMessage().observe(this, onMessageErrorObserver)
        viewModel.initRepo().listIsEmpty().observe(this, isEmptyListObserver)

    }

    private val renderMovies = Observer<List<NowPlayingMovie>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.updateData(it)
        swipeDashboard.isRefreshing = false

        adapter.onItemClick = { it ->
            //            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra("movieID", it.id)
            activity?.startActivity(intent)
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        layoutEmpty.visibility = View.GONE
        layoutError.visibility = View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error ($it)"
    }

    private val isEmptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
//        viewModel.initRepo().retrieveNowPlayingMovies().observe(this, renderMovies)
        setupViewModel()
        setupUI()
        startMinuteUpdater()
    }

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(broadcastReceiver)
    }

    private fun startMinuteUpdater() {
        var intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                setupViewModel()
                setupUI()
                Toast.makeText(activity, "updated", Toast.LENGTH_SHORT).show()
            }
        }
        context?.registerReceiver(broadcastReceiver, intentFilter)
    }
}