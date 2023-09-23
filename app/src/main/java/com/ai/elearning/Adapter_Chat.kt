package com.ai.elearning

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import android.app.UiModeManager
import android.content.res.Configuration
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class Adapter_Chat (private val context: Context, private val databaseRef: DatabaseReference, private val loading: LottieAnimationView,private val pengirim:String) :
    RecyclerView.Adapter<Adapter_Chat.Chat>() {
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
                    Toast.makeText(context, "Tidak ada pesan!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Terjadi kesalahan! Periksa koneksi internet", Toast.LENGTH_SHORT).show()
            }
        })
    }


    class Chat(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: CardView = itemView.findViewById(R.id.cardview_item_chat_id)
        val tv_pengirim: TextView = itemView.findViewById(R.id.tv_pengirim_id)
        val tv_waktu: TextView = itemView.findViewById(R.id.tv_waktu_id)
        val tv_pesan: TextView = itemView.findViewById(R.id.tv_pesan_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Chat { val itemView =
        LayoutInflater.from(parent.context).inflate(R.layout.umum_item_chat, parent, false)
        return Chat(itemView)
    }

    override fun onBindViewHolder(holder: Chat, position: Int) {
        val currentItem = dataList[position]
        val marginLayoutParams = holder.container.layoutParams as ViewGroup.MarginLayoutParams
        if (pengirim==currentItem.c_pengirim){
            holder.container.setCardBackgroundColor(context.getColor(R.color.colorPrimary))
            marginLayoutParams.setMargins(50.dpToPx(context), 5.dpToPx(context), 10.dpToPx(context), 0)
            holder.container.layoutParams = marginLayoutParams
        }else{
            holder.container.setCardBackgroundColor(context.getColor(R.color.chatclientdark))
            marginLayoutParams.setMargins(10.dpToPx(context), 5.dpToPx(context), 50.dpToPx(context), 0)
            holder.container.layoutParams = marginLayoutParams
        }
        holder.tv_pengirim.text = currentItem.c_pengirim
        holder.tv_waktu.text = currentItem.c_waktu
        holder.tv_pesan.text = currentItem.c_pesan
    }
    override fun getItemCount() = dataList.size

    fun Int.dpToPx(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).toInt()
    }

}