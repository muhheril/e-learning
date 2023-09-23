package com.ai.elearning

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.regex.Pattern

class Adapter_Video(private val context: Context, private val databaseRef: DatabaseReference) :
    RecyclerView.Adapter<Adapter_Video.Home>() {
        private var dataList: MutableList<Model_Data> = mutableListOf()
        init {
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()
                    for (postSnapshot in snapshot.children) {
                        val home = postSnapshot.getValue(Model_Data::class.java)
                        home?.let {
                            dataList.add(it)
                        }
                    }

                    if (dataList.isEmpty()) {
                    }
                    notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Terjadi kesalahan! Periksa koneksi internet", Toast.LENGTH_SHORT).show()
                }
            })
        }


        class Home(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val container_video: ConstraintLayout = itemView.findViewById(R.id.container_item_video_id)
            val tv_judul_video: TextView = itemView.findViewById(R.id.judul_video_id)
            val gambar: ImageView = itemView.findViewById(R.id.imagevideo_id)
            val loading: LottieAnimationView = itemView.findViewById(R.id.loading_item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Home { val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.umum_item_video, parent, false)
            return Home(itemView)
        }

        override fun onBindViewHolder(holder: Home, position: Int) {
            val currentItem = dataList[position]
            holder.tv_judul_video.text = currentItem.judul_video

            val videoUrl = extractYouTubeVideoUrlFromIframe(currentItem.link_video.toString())
            val videoId = extractVideoId(videoUrl)
            val thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

            Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(100,100)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.loading.visibility = View.GONE
                        holder.gambar.visibility = View.VISIBLE
                        holder.gambar.setImageResource(R.drawable.baseline_ondemand_video_24)
                        return true // Kembalikan true untuk menghentikan pengolahan lebih lanjut
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.loading.visibility = View.GONE
                        holder.gambar.visibility = View.VISIBLE
                        return false
                    }
                }).into(holder.gambar)



            holder.container_video.setOnClickListener {
                val intent = Intent(context, _11_Youtube_A::class.java)
                intent.putExtra("judul",currentItem.judul_video)
                intent.putExtra("link",currentItem.link_video)
                intent.putExtra("deskripsi",currentItem.deskripsi_video)
                context.startActivity(intent)
            }
        }

        override fun getItemCount() = dataList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2, RecyclerView.VERTICAL, false)
    }

    // Fungsi untuk mengekstrak ID video dari URL video YouTube
    private fun extractVideoId(videoUrl: String): String {
        val regex = "(?<=watch\\?v=|\\/videos\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed\\/?key=)[^#\\&\\?\\n]*"
        val pattern = Regex(regex)
        val matchResult = pattern.find(videoUrl)
        return matchResult?.value ?: ""
    }

    fun extractYouTubeVideoUrlFromIframe(iframe: String): String {
        val pattern = Pattern.compile("src=\"(.*?)\"")
        val matcher = pattern.matcher(iframe)

        if (matcher.find()) {
            val srcAttribute = matcher.group(1)
            val urlPattern = Pattern.compile("https://www.youtube.com/embed/(.*?)\\?")
            val urlMatcher = urlPattern.matcher(srcAttribute)

            if (urlMatcher.find()) {
                return "https://www.youtube.com/watch?v=${urlMatcher.group(1)}"
            }
        }

        return ""
    }



}
