package com.ai.elearning

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.webkit.JavascriptInterface
import android.widget.ProgressBar

class _11_Youtube_A : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var deskripsii: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: ConstraintLayout
    private var mCustomView: View? = null
    private var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
    private lateinit var mFullscreenContainer: FrameLayout
    private var mOriginalOrientation: Int = 0
    private var mOriginalSystemUiVisibility: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_11_youtube)

        toolbar = findViewById(R.id.toolbar_kembali_id)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{ onBackPressed() }
        webView=findViewById(R.id.webview_id)
        progressBar = findViewById(R.id.progressBar)
        deskripsii=findViewById(R.id.tv_deskripsivideo_id)
        appBarLayout=findViewById(R.id.appBarLayout1)
        mFullscreenContainer = findViewById(R.id.fullscreen_container)

        // Aktifkan JavaScript di WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = MyChrome()

        val judul = intent.getStringExtra("judul").toString()
        var link = intent.getStringExtra("link").toString()
        val deskripsi = intent.getStringExtra("deskripsi").toString()
        toolbar.setTitle(judul)
        deskripsii.text = deskripsi
        link = link.replace("width=\"560\"", "width=\"100%\"").replace("height=\"315\"", "height=\"100%\"")
        webView.loadData(link,"text/html","utf-8")

        // Handle configuration changes
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    inner class MyChrome : WebChromeClient() {
        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomView == null) null else BitmapFactory.decodeResource(applicationContext.resources, R.drawable.baseline_ondemand_video_24)
        }

        override fun onHideCustomView() {
            mFullscreenContainer.removeView(mCustomView)
            mCustomView = null
            window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
            requestedOrientation = mOriginalOrientation
            mCustomViewCallback?.onCustomViewHidden()
            mCustomViewCallback = null
            mFullscreenContainer.visibility = View.GONE

            // Ketika keluar dari mode layar penuh, kembalikan rotasi dan tampilkan elemen-elemen yang di-hide
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            appBarLayout.visibility = View.VISIBLE
            deskripsii.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            enterFullScreen()
        }

        override fun onShowCustomView(paramView: View?, paramCustomViewCallback: CustomViewCallback?) {
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            mCustomView = paramView
            mOriginalSystemUiVisibility = window.decorView.systemUiVisibility
            mOriginalOrientation = requestedOrientation
            mCustomViewCallback = paramCustomViewCallback
            mFullscreenContainer.visibility = View.VISIBLE
            mFullscreenContainer.addView(mCustomView, FrameLayout.LayoutParams(-1, -1))
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_FULLSCREEN

            // Ketika masuk ke mode layar penuh, ubah rotasi dan hide elemen-elemen tertentu
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            appBarLayout.visibility = View.GONE
            deskripsii.visibility = View.GONE
            toolbar.visibility = View.GONE
            enterFullScreen()
        }

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

    @JavascriptInterface
    fun enterFullScreen() {
        runOnUiThread {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    @JavascriptInterface
    fun exitFullScreen() {
        runOnUiThread {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}