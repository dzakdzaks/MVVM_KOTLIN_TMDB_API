package com.dzakdzaks.tmdb_mvvm_kotlin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.dzakdzaks.tmdb_mvvm_kotlin.MainApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * ==================================//==================================
 * ==================================//==================================
 * Created by Dzakdzaks on Tuesday, 21 January 2020 at 18:16.
 * Project Name => TMDB_MVVM_KOTLIN
 * Package Name => com.dzakdzaks.tmdb_mvvm_kotlin.utils
 * ==================================//==================================
 * ==================================//==================================
 */

class Utils {

    companion object {
        fun isConnectedToInternet(): Boolean {
            val connectivity = MainApplication.appContext().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info.indices)
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
            return false
        }

        fun showToastMessage(msg: String) {
            Toast.makeText(MainApplication.appContext(), msg, Toast.LENGTH_SHORT).show()
        }

        @SuppressLint("SimpleDateFormat")
        fun dateFormater(oldDate: String): String? {
            var newDate: String? = null
            val oldFormat = SimpleDateFormat("yyyy-mm-dd")
            val convertedDate: Date?

            try {
                convertedDate = oldFormat.parse(oldDate)
                val newFormat = SimpleDateFormat("MMMM dd, yyyy")
                newDate = newFormat.format(convertedDate!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return newDate
        }

        fun log(TAG: String, str: String) {
            if (str.length > 3000) {
                Log.i(TAG, str.substring(0, 3000))
                log(TAG, str.substring(3000))
            } else {
                Log.i(TAG, str) // continuation
            }
        }
    }

}