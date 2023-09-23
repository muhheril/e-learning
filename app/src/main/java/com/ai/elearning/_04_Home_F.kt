package com.ai.elearning

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class _04_Home_F : Fragment() {
    private lateinit var adapterVideo: Adapter_Video
    private lateinit var recyclerViewVideo:RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var db_rps : DatabaseReference
    private lateinit var db_video : DatabaseReference
    private lateinit var containerRPS: CardView
    private lateinit var containerModul: CardView
    private lateinit var containerMateri: CardView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment__04__home_, container, false)

        recyclerViewVideo = view.findViewById(R.id.recyclerview_item_video_id)
        containerRPS = view.findViewById(R.id.container_item_rps_id)
        containerModul = view.findViewById(R.id.container_item_modul_id)
        containerMateri = view.findViewById(R.id.container_item_materi_id)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        database = FirebaseDatabase.getInstance()
        db_rps = database.getReference("rps")
        db_rps.addListenerForSingleValueEvent(object : ValueEventListener {
            val judul = "RPS Mata Kuliah"
            override fun onDataChange(snapshot: DataSnapshot) {
                val link = snapshot.getValue(String::class.java).toString()
                containerRPS.setOnClickListener {
                    val intent = Intent(requireContext(),_08_PDF_Reader::class.java)
                    intent.putExtra("judul",judul)
                    intent.putExtra("link",link)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {Toast.makeText(requireContext(), "Terjadi kesalahan pada $judul", Toast.LENGTH_SHORT).show() }
        })

        db_video = database.getReference("video")
        swipeRefreshLayout.setOnRefreshListener {
            recyclerViewVideo.layoutManager = LinearLayoutManager(requireContext())
            adapterVideo = Adapter_Video(requireContext(),db_video)
            recyclerViewVideo.adapter = adapterVideo
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerViewVideo.layoutManager = LinearLayoutManager(requireContext())
        adapterVideo = Adapter_Video(requireContext(),db_video)
        recyclerViewVideo.adapter = adapterVideo


        containerModul.setOnClickListener {
            val intent = Intent(context,_09_List_Modul_A::class.java)
            startActivity(intent)
        }
        containerMateri.setOnClickListener {
            val intent = Intent(context,_10_List_Materi_A::class.java)
            startActivity(intent)
        }

        return view
    }

}