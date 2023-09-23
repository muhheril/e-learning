package com.ai.elearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class _05_Info_F : Fragment() {
    private lateinit var adapterInfo: Adapter_Info
    private lateinit var recyclerViewInfo: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var db_info : DatabaseReference
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var loading: LottieAnimationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment__05__info_, container, false)

        recyclerViewInfo = view.findViewById(R.id.recyclerview_item_info_id)
        loading = view.findViewById(R.id.loading_item)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        database = FirebaseDatabase.getInstance()
        db_info = database.getReference("pengumuman")
        swipeRefreshLayout.setOnRefreshListener {
            recyclerViewInfo.layoutManager = LinearLayoutManager(requireContext())
            adapterInfo = Adapter_Info(requireContext(),db_info,loading)
            recyclerViewInfo.adapter = adapterInfo
            swipeRefreshLayout.isRefreshing = false
        }
        recyclerViewInfo.layoutManager = LinearLayoutManager(requireContext())
        adapterInfo = Adapter_Info(requireContext(),db_info,loading)
        recyclerViewInfo.adapter = adapterInfo

        return view
    }

}