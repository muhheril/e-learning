package com.ai.elearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class _10_List_Materi_A : AppCompatActivity() {

    private lateinit var adapterMateri: Adapter_Materi
    private lateinit var recyclerViewMateri: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var db_materi : DatabaseReference
    private lateinit var loading: LottieAnimationView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_10_list_materi)

        loading=findViewById(R.id.loading_item)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        val toolbar: Toolbar = findViewById(R.id.toolbar_kembali_id)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{ onBackPressed() }

        recyclerViewMateri = findViewById(R.id.recyclerview_item_materi_id)

        swipeRefreshLayout.setOnRefreshListener {
            database = FirebaseDatabase.getInstance()
            db_materi = database.getReference("materi")
            recyclerViewMateri.layoutManager = LinearLayoutManager(this@_10_List_Materi_A)
            adapterMateri = Adapter_Materi(this@_10_List_Materi_A,db_materi,loading)
            recyclerViewMateri.adapter = adapterMateri
            swipeRefreshLayout.isRefreshing = false
        }

        database = FirebaseDatabase.getInstance()
        db_materi = database.getReference("materi")
        recyclerViewMateri.layoutManager = LinearLayoutManager(this@_10_List_Materi_A)
        adapterMateri = Adapter_Materi(this@_10_List_Materi_A,db_materi,loading)
        recyclerViewMateri.adapter = adapterMateri
    }

    override fun onBackPressed() {
        finish()
    }
}