package com.dzakdzaks.tmdb_mvvm_kotlin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dzakdzaks.tmdb_mvvm_kotlin.MainApplication
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import com.dzakdzaks.tmdb_mvvm_kotlin.callback.OperationCallback
import com.dzakdzaks.tmdb_mvvm_kotlin.data.local.LocalRepository
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.Book
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.RequestBookUpdate
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.book.ResponseUpdateDeleteBook
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie.NowPlayingMovie
import com.dzakdzaks.tmdb_mvvm_kotlin.data.model.movie_detail.ResponseMovieDetail
import com.dzakdzaks.tmdb_mvvm_kotlin.data.remote.RemoteRepository
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils
import com.google.gson.Gson

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 17:53.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.data
 * ==================================//==================================
 * ==================================//==================================
 */

class PublicRepository constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : PublicDataSource {

    /*global function*/
    private val _isViewLoading = MutableLiveData<Boolean>()
    private val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    private val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    private val isEmptyList: LiveData<Boolean> = _isEmptyList
    /*global function*/


    private val _nowPlayingMovies =
        MutableLiveData<List<NowPlayingMovie>>().apply { value = emptyList() }
    private val nowPlayingMovie: LiveData<List<NowPlayingMovie>> = _nowPlayingMovies

    private val _detailMovie = MutableLiveData<ResponseMovieDetail>()
    private val detailMovie: LiveData<ResponseMovieDetail> = _detailMovie

    private val _allBooks = MutableLiveData<List<Book>>().apply { value = emptyList() }
    private val allBooks: LiveData<List<Book>> = _allBooks

    private val _updateBookMsg = MutableLiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete>()
    private val updateBookMsg: LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete> =
        _updateBookMsg

    override fun isLoading(): LiveData<Boolean> {
        return isViewLoading
    }

    override fun onErrorMessage(): LiveData<Any> {
        return onMessageError
    }

    override fun listIsEmpty(): LiveData<Boolean> {
        return isEmptyList
    }

    private fun getLocalNowPlayingMovies(): List<NowPlayingMovie>? {
        var list: List<NowPlayingMovie>? = null
        Thread {
            Thread.sleep(10)
            list = localRepository.getNowPlayingMovies()
        }.start()
        Thread.sleep(25)
        return list
    }

    override fun retrieveNowPlayingMovies(): LiveData<List<NowPlayingMovie>> {
        if (!Utils.isConnectedToInternet()) {
//            Utils.showToast(MainApplication.appContext().resources.getString(R.string.no_internet))
//            _onMessageError.value = MainApplication.appContext().resources.getString(R.string.no_internet)
            _nowPlayingMovies.value = getLocalNowPlayingMovies()
        } else {
            _isViewLoading.value = true
            remoteRepository.retrieveNowPlayingMovies(object : OperationCallback {
                override fun onSuccess(obj: Any?) {
                    _isViewLoading.value = false
                    if (obj != null && obj is List<*>) {
                        if (obj.isEmpty()) {
                            _isEmptyList.value = true
                        } else {
                            _nowPlayingMovies.value = obj as List<NowPlayingMovie>
                            Thread {
                                Thread.sleep(10)
                                for (item in obj) {
                                    val data = NowPlayingMovie()
                                    data.id = item.id
                                    data.overview = item.overview
                                    data.originalLanguage = item.originalLanguage
                                    data.originalTitle = item.originalTitle
                                    data.video = item.video
                                    data.title = item.title
                                    data.posterPath = item.posterPath
                                    data.backdropPath = item.backdropPath
                                    data.releaseDate = item.releaseDate
                                    data.popularity = item.popularity
                                    data.voteAverage = item.voteAverage
                                    data.adult = item.adult
                                    data.voteCount = item.voteCount
                                    localRepository.saveNowPlayingMovies(data)
                                }
                                Log.d(
                                    "datalocal",
                                    Gson().toJson(localRepository.getNowPlayingMovies())
                                )
                            }.start()
                        }
                    }
                }

                override fun onError(obj: Any?) {
                    _isViewLoading.value = false
                    _onMessageError.value = obj
                }

            })
        }
        return nowPlayingMovie
    }

    override fun retrieveMovieDetail(movieID: Int): LiveData<ResponseMovieDetail> {
        if (!Utils.isConnectedToInternet()) {
            _onMessageError.value =
                MainApplication.appContext().resources.getString(R.string.no_internet)
        } else {
            _isViewLoading.value = true
            remoteRepository.retrieveMovieDetail(movieID, object : OperationCallback {
                override fun onSuccess(obj: Any?) {
                    _isViewLoading.value = false
                    if (obj != null) {
                        _detailMovie.value = obj as ResponseMovieDetail
                    }
                }

                override fun onError(obj: Any?) {
                    _isViewLoading.value = false
                    _onMessageError.value = obj
                }

            })
        }

        return detailMovie
    }

    override fun retrieveAllBooks(): LiveData<List<Book>> {
        if (!Utils.isConnectedToInternet()) {
            _onMessageError.value =
                MainApplication.appContext().resources.getString(R.string.no_internet)
        } else {
            _isViewLoading.value = true
            remoteRepository.retrieveAllBooks(object : OperationCallback {
                override fun onSuccess(obj: Any?) {
                    _isViewLoading.value = false

                    if (obj != null && obj is List<*>) {
                        if (obj.isEmpty()) {
                            _isEmptyList.value = true
                        } else {
                            _allBooks.value = obj as List<Book>
                        }
                    }
                }

                override fun onError(obj: Any?) {
                    _isViewLoading.value = false
                    _onMessageError.value = obj
                }

            })
        }
        return allBooks
    }

    override fun bookUpdate(
        id: Int,
        requestBookUpdate: RequestBookUpdate
    ): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        if (!Utils.isConnectedToInternet()) {
            _onMessageError.value =
                MainApplication.appContext().resources.getString(R.string.no_internet)
        } else {
            _isViewLoading.value = true
            remoteRepository.bookUpdate(id, requestBookUpdate, object : OperationCallback {
                override fun onSuccess(obj: Any?) {
                    _isViewLoading.value = false
                    if (obj != null) {
                        _updateBookMsg.value = obj as ResponseUpdateDeleteBook.ResponseUpdateDelete
                    }
                }

                override fun onError(obj: Any?) {
                    _isViewLoading.value = false
                    _onMessageError.value = obj
                }

            })
        }
        return updateBookMsg
    }

    override fun bookDelete(id: Int): LiveData<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        if (!Utils.isConnectedToInternet()) {
            _onMessageError.value =
                MainApplication.appContext().resources.getString(R.string.no_internet)
        } else {
            _isViewLoading.value = true
            remoteRepository.bookDelete(id, object : OperationCallback {
                override fun onSuccess(obj: Any?) {
                    _isViewLoading.value = false
                    if (obj != null) {
                        _updateBookMsg.value = obj as ResponseUpdateDeleteBook.ResponseUpdateDelete
                    }
                }

                override fun onError(obj: Any?) {
                    _isViewLoading.value = false
                    _onMessageError.value = obj
                }

            })
        }
        return updateBookMsg
    }
}