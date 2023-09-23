package com.ai.elearning

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class Adapter_Info (private val context: Context, private val databaseRef: DatabaseReference, private val loading: LottieAnimationView) :
    RecyclerView.Adapter<Adapter_Info.Info>() {
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
                notifyDataSetChanged()
                if (dataList.isNotEmpty()) {
                    loading.visibility= View.GONE
                } else {
                    loading.visibility= View.GONE
                    Toast.makeText(context, "Tidak ada pengumuman!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Terjadi kesalahan! Periksa koneksi internet", Toast.LENGTH_SHORT).show()
            }
        })
    }


    class Info(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_pengirim: TextView = itemView.findViewById(R.id.tv_judul_id)
        val tv_pesan: TextView = itemView.findViewById(R.id.tv_pesan_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Info { val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.umum_item_info, parent, false)
        return Info(itemView)
    }

    override fun onBindViewHolder(holder: Info, position: Int) {
        val currentItem = dataList[position]
        holder.tv_pengirim.text = "${currentItem.i_pengirim}   |   ${currentItem.i_waktu}"
        holder.tv_pesan.text = currentItem.i_pesan
    }
    override fun getItemCount() = dataList.size
}