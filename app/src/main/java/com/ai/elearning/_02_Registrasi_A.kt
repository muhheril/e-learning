package com.ai.elearning

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class _02_Registrasi_A : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextNIM: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextKonfirmasiPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var usersRef: DatabaseReference
    private lateinit var spinnerKelas: Spinner
    private lateinit var spinnerJurusan: Spinner

    val kelasOptions = arrayOf("A", "B", "C", "D", "E")
    val jurusanOptions = arrayOf("Teknik - Sipil", "Teknik - Informatika", "Teknik - Perencanaan Wilayah Kota")

    var kelas = ""
    var jurusan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_02_registrasi)

        editTextName = findViewById(R.id.editTextName)
        editTextNIM = findViewById(R.id.editTextNIM)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextKonfirmasiPassword = findViewById(R.id.editTextKonfirmasiPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        spinnerKelas = findViewById(R.id.spinnerKelas)
        spinnerJurusan = findViewById(R.id.spinnerJurusan)


        auth = FirebaseAuth.getInstance()

        usersRef = FirebaseDatabase.getInstance().getReference()

        val toolbar: Toolbar = findViewById(R.id.toolbar_kembali_id)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{ onBackPressed() }

        // Inisialisasi adapter untuk Spinner Kelas
        val kelasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelasOptions)
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKelas.adapter = kelasAdapter

        // Inisialisasi adapter untuk Spinner Jurusan
        val jurusanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, jurusanOptions)
        jurusanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJurusan.adapter = jurusanAdapter


        buttonRegister.setOnClickListener {
            val nama = editTextName.text.toString()
            val nim = editTextNIM.text.toString()
            val alamat = editTextAddress.text.toString()
            val nohp = editTextPhoneNumber.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val konfirmasipassword = editTextKonfirmasiPassword.text.toString()

            kelas = if (spinnerKelas.selectedItem != null) {
                spinnerKelas.selectedItem.toString()
            } else {
                ""
            }

            jurusan = if (spinnerJurusan.selectedItem != null) {
                spinnerJurusan.selectedItem.toString()
            } else {
                ""
            }

            if (nama.isNotEmpty() && nim.isNotEmpty() && kelas.isNotEmpty() && jurusan.isNotEmpty() &&
                alamat.isNotEmpty() && nohp.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && konfirmasipassword.isNotEmpty()) {

                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.umum_dialog_loading)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                if (password.equals(konfirmasipassword)){
                    auth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val signInMethods = task.result?.signInMethods
                                if (signInMethods.isNullOrEmpty()) {
                                    // Akun belum terdaftar, Anda dapat melanjutkan proses pendaftaran
                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this) { registrationTask ->
                                            if (registrationTask.isSuccessful) {
                                                val userId = auth.currentUser?.uid
                                                if (userId != null) {
                                                    val userData = HashMap<String, Any>()
                                                    userData["userId"] = userId
                                                    userData["nama"] = nama
                                                    userData["nim"] = nim
                                                    userData["kelas"] = kelas
                                                    userData["jurusan"] = jurusan
                                                    userData["alamat"] = alamat
                                                    userData["nohp"] = nohp
                                                    userData["email"] = email
                                                    userData["profil"] = "-"

                                                    usersRef.child("mahasiswa").child(userId).setValue(userData)
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                // Data terunggah dengan sukses
                                                                dialog.dismiss()
                                                                Toast.makeText(this@_02_Registrasi_A, "Registrasi berhasil.", Toast.LENGTH_SHORT).show()
                                                                // Lanjutkan ke aktivitas berikutnya
                                                                val intent = Intent(this@_02_Registrasi_A, _01_Login_A::class.java)
                                                                startActivity(intent)
                                                                finish()
                                                            } else {
                                                                // Gagal mengunggah data, tangani error di sini
                                                                dialog.dismiss()
                                                                val errorMessage = "Gagal mengunggah data: ${task.exception?.message}"
                                                                Toast.makeText(this@_02_Registrasi_A, errorMessage, Toast.LENGTH_SHORT).show()
                                                                // Tambahkan pesan kesalahan ke logcat
                                                                Log.e("_02_Registrasi_A", errorMessage)
                                                            }
                                                        }
                                                        .addOnFailureListener {
                                                            dialog.dismiss()
                                                            val errorMessage = "Gagal mengunggah data. Periksa koneksi internet anda: ${it.message}"
                                                            Toast.makeText(this@_02_Registrasi_A, errorMessage, Toast.LENGTH_SHORT).show()
                                                            // Tambahkan pesan kesalahan ke logcat
                                                            Log.e("_02_Registrasi_A", errorMessage)
                                                        }

                                                }
                                            } else {
                                                dialog.dismiss()
                                                Toast.makeText(this@_02_Registrasi_A, "Registrasi gagal. Periksa email dan password Anda.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                } else {
                                    // Akun sudah terdaftar
                                    Toast.makeText(this@_02_Registrasi_A, "Akun sudah terdaftar.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                dialog.dismiss()
                                Toast.makeText(this@_02_Registrasi_A, "Gagal memeriksa status akun.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this@_02_Registrasi_A, "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show()
                }

                // Cek apakah akun sudah terdaftar di Firebase Authentication

            } else {
                Toast.makeText(this@_02_Registrasi_A, "Isi semua informasi yang diperlukan.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}