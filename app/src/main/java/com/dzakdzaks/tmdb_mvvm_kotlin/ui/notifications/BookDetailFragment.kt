package com.dzakdzaks.tmdb_mvvm_kotlin.ui.notifications


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.checkSelfPermission
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
import com.dzakdzaks.tmdb_mvvm_kotlin.ui.photo_view.PhotoPreviewActivity
import com.dzakdzaks.tmdb_mvvm_kotlin.utils.Utils
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.MainViewModel
import com.dzakdzaks.tmdb_mvvm_kotlin.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_book_detail.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class BookDetailFragment(private var onAddSuccessListener: OnAddSuccessListener) : DialogFragment() {

    private lateinit var viewModel: MainViewModel

    interface OnAddSuccessListener {
        fun onSuccessAdd(dialogFragment: DialogFragment)
    }

    companion object {
        private const val PERMISSION_CODE = 0
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private const val REQUEST_TAKE_PHOTO = 2
        var onSuccess: OnAddSuccessListener? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSuccess = onAddSuccessListener

        val isDetail = arguments?.getBoolean("isDetail")

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
                .get(MainViewModel::class.java)

        if (isDetail!!) {

            val bundle = arguments?.getInt("bookID")

            viewModel.initRepo().retrieveBookByID(bundle!!).observe(this, renderDetailBook)
            viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)
        } else {
            storeBook()
        }

        ic_close.setOnClickListener {
            dismiss()
        }

    }

    private fun storeBook() {
        progressBarNotifDetail.visibility = View.GONE
        btnBack.text = "Save"

        btnBack.setOnClickListener {
            //todo save data
        }

        ivPreviewPhoto.setOnClickListener {
            //todo choose image from camera or gallery
            val builder = AlertDialog.Builder(activity)

            // Set the alert dialog title
            builder.setTitle("Choose Image")

            // Display a message on alert dialog
            builder.setMessage("From gallery or take a picture?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Take a picture") { dialog, which ->
                Utils.showToastMessage("On Development Progress")
            }


            // Display a negative button on alert dialog
            builder.setNegativeButton("Gallery") { dialog, which ->
                checkPermissionImageGallery()
            }


            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel") { _, _ ->

            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
    }

    @SuppressLint("WrongConstant")
    private fun checkPermissionImageGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                //permission already granted
                pickImageFromGallery()
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
    }

    private fun takeAPicture() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)
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

        ivPreviewPhoto.setOnClickListener {
            val i = Intent(activity, PhotoPreviewActivity::class.java)
            i.putExtra("url", imgURL)
            startActivity(i)
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(DashboardFragment.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBarNotifDetail.visibility = visibility
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()
                ) {
                    if (grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                    )
                        pickImageFromGallery()
                    else
                        Utils.showToastMessage("Permission Denied")
                } else {
                    Utils.showToastMessage("Grant result is empty")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGE_IN_ALBUM ->
                    getDataImage(data)
//                REQUEST_TAKE_PHOTO ->
//                    getDataImage(data)
            }
        }
    }

    private fun getDataImage(data: Intent?) {
        Glide.with(activity!!)
            .load(data?.data)
            .into(ivPreviewPhoto)

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        val finalFile = File(getRealPathFromURI(data?.data!!))
        Log.d("fakfak", finalFile.absolutePath)

        tvName.text = Utils.randomAlphanum(10)
        tvAuthor.text = Utils.randomAlphanum(10)

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), finalFile)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", Utils.randomAlphanum(10), requestFile)
        val name: RequestBody = RequestBody.create(MultipartBody.FORM, tvName.text.toString())
        val author: RequestBody = RequestBody.create(MultipartBody.FORM, tvAuthor.text.toString())

        btnBack.setOnClickListener {
            viewModel.initRepo().storeABook(name, author, body).observe(this, responseStoreBook)
            btnBack.visibility = View.GONE
            viewModel.initRepo().isLoading().observe(this, isViewLoadingObserver)
        }
    }

    private val responseStoreBook = Observer<ResponseUpdateDeleteBook.ResponseUpdateDelete> {
        Utils.showToastMessage(it.message.toString())
        btnBack.visibility = View.VISIBLE
        onSuccess!!.onSuccessAdd(this)
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = activity?.contentResolver?.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val idx = cursor!!.getColumnIndex(Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }


}
