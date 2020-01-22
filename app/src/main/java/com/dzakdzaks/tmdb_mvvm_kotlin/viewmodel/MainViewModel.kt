package com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.data.PublicRepository

class MainViewModel(private val repository: PublicRepository) : ViewModel() {

    fun initRepo(): PublicRepository {
        return repository
    }
}