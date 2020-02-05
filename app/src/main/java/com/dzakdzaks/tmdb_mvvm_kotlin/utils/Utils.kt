package com.dzakdzaks.tmdb_mvvm_kotlin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.dzakdzaks.tmdb_mvvm_kotlin.MainApplication
import com.dzakdzaks.tmdb_mvvm_kotlin.R
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

        fun randomAlphanum(length: Int): String {
            val charstring = "abcdefghijklmnopqrstuvwxyz0123456789"
            var randalphanum = ""
            var randroll: Double
            var randchar: String
            for (i in 0 until length) {
                randroll = Math.random()
                randchar = ""
                for (j in 1..35) {
                    if (randroll <= 1.0 / 36.0 * j) {
                        randchar = Character.toString(charstring[j - 1])
                        break
                    }
                }
                randalphanum += randchar
            }
            return randalphanum
        }

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

        fun setShowDialogFragment(
            activity: FragmentActivity,
            fragment: DialogFragment,
            bundle: Bundle
        ) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_dialog, fragment)
            //dissmis the dialog when onbackpressed
            transaction.addToBackStack(null);
            fragment.arguments = bundle
            transaction.commit()

        }
    }

}