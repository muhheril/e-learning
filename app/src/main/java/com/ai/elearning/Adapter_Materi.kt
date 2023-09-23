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

class Adapter_Materi(private val context: Context, private val databaseRef: DatabaseReference,private val loading:LottieAnimationView) :
    RecyclerView.Adapter<Adapter_Materi.Home>() {
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
                    loading.visibility=View.GONE
                } else {
                    loading.visibility=View.GONE
                    Toast.makeText(context, "Tidak ada data!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Terjadi kesalahan! Periksa koneksi internet", Toast.LENGTH_SHORT).show()
            }
        })
    }


    class Home(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container_materi: ConstraintLayout = itemView.findViewById(R.id.container_item_id)
        val tv_judul_materi: TextView = itemView.findViewById(R.id.tv_judul_id)
        val tv_deskripsi_materi: TextView = itemView.findViewById(R.id.tv_deskripsi_id)
        val icon_join: ImageView = itemView.findViewById(R.id.icon_join_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Home {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.umum_item_materi, parent, false)
        return Home(itemView)
    }

    override fun onBindViewHolder(holder: Home, position: Int) {
        val currentItem = dataList[position]
        holder.tv_judul_materi.text = currentItem.judul_materi
        holder.tv_deskripsi_materi.text = currentItem.deskripsi_materi
        holder.container_materi.setOnClickListener {jointoactivity(currentItem)}
        holder.icon_join.setOnClickListener { jointoactivity(currentItem)}
    }

    private fun jointoactivity(currentItem: Model_Data) {
        val intent = Intent(context, _08_PDF_Reader::class.java)
        intent.putExtra("judul",currentItem.judul_materi)
        intent.putExtra("link", currentItem.link_materi)
        context.startActivity(intent)
    }

    override fun getItemCount() = dataList.size

}
