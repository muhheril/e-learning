package com.ai.elearning

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.Window
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class Umum_FullscreenImageDialog (context: Context, private val imageUrl: String) : Dialog(context) {
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scale = 1.0f
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.activity_umum_fullscreen_image_dialog)

        val imageView = findViewById<PhotoView>(R.id.imageViewFullscreen)
        val loadingFoto = findViewById<LottieAnimationView>(R.id.loading_item_id)
        Glide.with(context).load(imageUrl).listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                loadingFoto.visibility = View.GONE
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                loadingFoto.visibility = View.GONE
                Toast.makeText(context, "Terjadi kesalahan periksa koneksi internet", Toast.LENGTH_SHORT).show()
                return true
            }
        }).into(imageView)

        // Tambahkan listener untuk mengatur zoom saat double-tap
        var isZoomed = false
        imageView.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (isZoomed) {
                    imageView.setScale(1f, true)
                    isZoomed = false
                } else {
                    imageView.setScale(2f, true)
                    isZoomed = true
                }
                return true
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                return false
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                return false
            }
        })


        imageView.setOnClickListener {
            dismiss()
        }
    }
}