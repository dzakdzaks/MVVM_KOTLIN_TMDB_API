package com.dzakdzaks.tmdb_mvvm_kotlin.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.callback.OnclickAdapter
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.RequestBookUpdate
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseBooks
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.di.Injection
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard.DashboardFragment
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.MainViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.layout_error.*

class NotificationsFragment : Fragment(), OnclickAdapter {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter =
            NotificationAdapter(viewModel.initRepo().retrieveAllBooks().value ?: emptyList(), this)

//        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        recyclerViewNotif.layoutManager = LinearLayoutManager(context)
//        else
//            recyclerView.layoutManager = GridLayoutManager(this, 4)

        recyclerViewNotif.itemAnimator = DefaultItemAnimator()
        recyclerViewNotif.adapter = adapter

        swipeDashboardNotif.setOnRefreshListener {
            viewModel.initRepo().retrieveAllBooks().observe(this, renderMovies)
        }
        swipeDashboardNotif.setColorSchemeColors(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimaryDark),
            resources.getColor(android.R.color.darker_gray)
        )
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainViewModel::class.java)
        viewModel.initRepo().retrieveAllBooks().observe(this, renderMovies)

        viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)
        viewModel.initRepo().onErrorMessage().observe(this, onMessageErrorObserver)
        viewModel.initRepo().listIsEmpty().observe(this, isEmptyListObserver)

    }

    private val renderMovies = Observer<List<Book>> {
        Log.v(DashboardFragment.TAG, "data updated $it")
        layoutErrorNotif.visibility = View.GONE
        layoutEmptyNotif.visibility = View.GONE
        recyclerViewNotif.visibility = View.VISIBLE
        adapter.updateData(it)
        swipeDashboardNotif.isRefreshing = false
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(DashboardFragment.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        layoutEmptyNotif.visibility = View.GONE
        layoutErrorNotif.visibility = View.GONE
        progressBarNotif.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(DashboardFragment.TAG, "onMessageError $it")
        layoutErrorNotif.visibility = View.VISIBLE
        layoutEmptyNotif.visibility = View.GONE
        textViewError.text = "Error ($it)"
    }

    private val isEmptyListObserver = Observer<Boolean> {
        Log.v(DashboardFragment.TAG, "emptyListObserver $it")
        layoutErrorNotif.visibility = View.GONE
        layoutEmptyNotif.visibility = View.VISIBLE
        recyclerViewNotif.visibility = View.GONE
    }

    override fun onItemClick(any: Any) {
        val book = any as Book
        Toast.makeText(activity, "You clicked : " + book.name, Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(any: Any, view: View) {
        val book = any as Book
        val popupMenu = PopupMenu(activity, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionEdit ->
                    editData(book)
//                    Toast.makeText(activity, "You edit : " + book.name, Toast.LENGTH_SHORT).show()
                R.id.actionDelete ->
                    deleteData(book)
//                    Toast.makeText(activity, "You delete : " + book.name, Toast.LENGTH_SHORT).show()
            }
            true
        }
        popupMenu.show()
    }

    private fun editData(book: Book) {
        val requestBookUpdate = RequestBookUpdate()
        requestBookUpdate.name = Utils.randomAlphanum(10)
        requestBookUpdate.author = Utils.randomAlphanum(10)
        viewModel.initRepo().bookUpdate(book.id!!, requestBookUpdate).observe(this, renderMoviesUpdate)
    }

    private fun deleteData(book: Book) {
        viewModel.initRepo().bookDelete(book.id!!).observe(this, renderMoviesDelete)
    }

    private val renderMoviesUpdate = Observer<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        Utils.showToastMessage(it.message.toString())
        viewModel.initRepo().retrieveAllBooks().observe(this, renderMovies)
    }

    private val renderMoviesDelete = Observer<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        Utils.showToastMessage(it.message.toString())
        viewModel.initRepo().retrieveAllBooks().observe(this, renderMovies)
    }
}