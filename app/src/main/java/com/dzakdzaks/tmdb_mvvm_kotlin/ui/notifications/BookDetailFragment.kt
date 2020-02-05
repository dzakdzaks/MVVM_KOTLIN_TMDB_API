package com.dzakdzaks.tmdb_mvvm_kotlin.ui.notifications


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dzakdzaks.tmdb_mvvm_kotlin.BuildConfig
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.di.Injection
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.dashboard.DashboardFragment
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.MainViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_book_detail.*
import kotlinx.android.synthetic.main.fragment_notifications.*


/**
 * A simple [Fragment] subclass.
 */
class BookDetailFragment : DialogFragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments?.getInt("bookID")


        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainViewModel::class.java)

        viewModel.initRepo().retrieveBookByID(bundle!!).observe(this, renderDetailBook)
        viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)

    }

    private val renderDetailBook = Observer<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        Utils.showToastMessage(it.message.toString())

        val book: ResponseUpdateDeleteBook.Data? = it.data

        val imgURL = BuildConfig.SERVER_HOST + book?.image

        Glide.with(ivPreviewPhoto.context)
            .load(imgURL)
            .into(ivPreviewPhoto)

        tvName.text = "Book Name : ${book?.name}"
        tvAuthor.text = "Book Author : ${book?.author}"
        tvCreated.text = "Created At : ${book?.createdAt}"
        tvUpdated.text = "updated At : ${book?.updatedAt}"

        btnBack.setOnClickListener {
            dismiss()
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(DashboardFragment.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBarNotifDetail.visibility = visibility
    }


}
