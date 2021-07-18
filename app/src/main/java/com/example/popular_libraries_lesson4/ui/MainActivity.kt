package com.example.popular_libraries_lesson4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.popular_libraries_lesson4.coverter.Converter
import com.example.popular_libraries_lesson4.databinding.ActivityMainBinding
import com.example.popular_libraries_lesson4.mvp.model.Image
import com.example.popular_libraries_lesson4.mvp.presenter.MainPresenter
import com.example.popular_libraries_lesson4.mvp.view.MainView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null
    private val presenter by moxyPresenter {
        MainPresenter(
            Converter(this),
            AndroidSchedulers.mainThread()
        )
    }
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)

        vb?.button?.setOnClickListener { presenter.buttonClick() }
    }

    override fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            SELECT_IMAGE_REQUEST_ID
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_ID && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                presenter.imageResult(Image(uri))
            }
        }
    }

    override fun onConvertComplete() {
        Toast.makeText(this, "Complete!", Toast.LENGTH_LONG).show()
    }

    override fun onConvertError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()
    }

    override fun showConvertDialog() {
        snackbar = Snackbar.make(vb!!.root, "Выполняется конвертация...", Snackbar.LENGTH_INDEFINITE)
            .setAction("Отменить") {
                presenter.cancelClick()
            }
        snackbar.show()
    }

    override fun hideConvertDialog() {
        snackbar.dismiss()
    }

    companion object {
        private const val SELECT_IMAGE_REQUEST_ID = 1
    }
}