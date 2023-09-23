package com.ai.elearning

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class _06_Pesan_F : Fragment() {

    private lateinit var adapterChat: Adapter_Chat
    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var db_chat : DatabaseReference
    private lateinit var db_user : DatabaseReference
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var loading: LottieAnimationView
    private lateinit var etPesan: EditText
    private lateinit var linearlayoutKirimpesan: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment__06__pesan_, container, false)


        recyclerViewChat = view.findViewById(R.id.recyclerview_item_chat_id)
        loading = view.findViewById(R.id.loading_item_chat_id)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        etPesan = view.findViewById(R.id.editTextChat_id)
        linearlayoutKirimpesan = view.findViewById(R.id.kirimpesan_linearlayout_id)

        database = FirebaseDatabase.getInstance()
        db_chat = database.getReference("pesan")
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        db_user = database.getReference("mahasiswa").child(userId.toString())
        linearlayoutKirimpesan.visibility=View.GONE

        swipeRefreshLayout.setOnRefreshListener {
            db(userId)
            swipeRefreshLayout.isRefreshing = false
        }
        db(userId)

        etPesan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) { linearlayoutKirimpesan.visibility=View.VISIBLE
                } else {linearlayoutKirimpesan.visibility=View.GONE}
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun db(userId: String?) {
        if (userId!=null) {
            db_user.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val pengirim = dataSnapshot.child("email").getValue(String::class.java).toString()
                        recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())
                        adapterChat= Adapter_Chat(requireContext(),db_chat,loading,pengirim)
                        recyclerViewChat.adapter = adapterChat
                        mulai(pengirim)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }

    }

    private fun mulai(pengirim: String) {
        linearlayoutKirimpesan.setOnClickListener {
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy   |   HH:mm")
            val formattedDate = dateFormat.format(currentDate)
            val tambahchat = HashMap<String, Any>()
            tambahchat["c_pengirim"] = pengirim
            tambahchat["c_waktu"] = formattedDate
            tambahchat["c_pesan"] = etPesan.text.toString()
            db_chat.push().setValue(tambahchat).addOnSuccessListener {
                etPesan.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal mengirim pesan! Periksa koneksi internet.", Toast.LENGTH_SHORT).show()
            }
            mulai(pengirim)
        }

    }

}