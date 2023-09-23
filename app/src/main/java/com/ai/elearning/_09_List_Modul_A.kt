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

class _09_List_Modul_A : AppCompatActivity() {

    private lateinit var adapterModul: Adapter_Modul
    private lateinit var recyclerViewMateri: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var db_modul : DatabaseReference
    private lateinit var loading: LottieAnimationView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_09_list_modul)

        val toolbar: Toolbar = findViewById(R.id.toolbar_kembali_id)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{ onBackPressed() }

        recyclerViewMateri = findViewById(R.id.recyclerview_item_modul_id)
        loading = findViewById(R.id.loading_item)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)


        swipeRefreshLayout.setOnRefreshListener {
            database = FirebaseDatabase.getInstance()
            db_modul = database.getReference("modul")
            recyclerViewMateri.layoutManager = LinearLayoutManager(this@_09_List_Modul_A)
            adapterModul = Adapter_Modul(this@_09_List_Modul_A,db_modul,loading)
            recyclerViewMateri.adapter = adapterModul
            swipeRefreshLayout.isRefreshing = false
        }

        database = FirebaseDatabase.getInstance()
        db_modul = database.getReference("modul")
        recyclerViewMateri.layoutManager = LinearLayoutManager(this@_09_List_Modul_A)
        adapterModul = Adapter_Modul(this@_09_List_Modul_A,db_modul,loading)
        recyclerViewMateri.adapter = adapterModul
    }

    override fun onBackPressed() {
        finish()
    }

}