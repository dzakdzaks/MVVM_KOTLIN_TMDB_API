package com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakdzaks.tmdb_mvvm_kotlin.data.PublicRepository

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:23.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel
 * ==================================//==================================
 * ==================================//==================================
 */

class ViewModelFactory(private val repository: PublicRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel with name " + modelClass.name + " not found.")
    }

}