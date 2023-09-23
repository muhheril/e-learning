package com.ai.elearning

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.View
import android.webkit.WebChromeClient
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout

class _08_PDF_Reader : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var header: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_08_pdf_reader)
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        header=findViewById(R.id.header_id)

        val toolbar: Toolbar = findViewById(R.id.toolbar_kembali_id)
        val judul = intent.getStringExtra("judul").toString()
        var link = intent.getStringExtra("link").toString()
        toolbar.setTitle(judul)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        checkOrientation(resources.configuration.orientation)
        setupWebView(link)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(link:String) {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        // Menambahkan pemantauan onPageFinished untuk menghilangkan ProgressBar setelah halaman selesai dimuat.
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

        webView.loadUrl(link)

    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        checkOrientation(newConfig.orientation)
    }

    private fun checkOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            header.visibility=View.GONE
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            header.visibility=View.VISIBLE
        }
    }

    override fun onBackPressed() {
        finish()
    }

}