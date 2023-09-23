package com.ai.elearning

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class _01_Login_A : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_01_login)
        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        register = findViewById(R.id.buttonRegister)

        register.setOnClickListener {
            val intent = Intent(this, _02_Registrasi_A::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.umum_dialog_loading)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("isLoggedIn", true)
                                val userID = user?.uid
                                if (userID != null) {
                                    editor.putString("userID", userID)
                                }
                                editor.apply()

                            Toast.makeText(this, "Login berhasil.", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            // Login berhasil, arahkan ke aktivitas selanjutnya
                            val intent = Intent(this, _03_Dashboard_A::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            // Login gagal, tampilkan pesan kesalahan
                            dialog.dismiss()
                            Toast.makeText(this, "Login gagal. Periksa email dan password Anda.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Isi email dan password Anda.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}