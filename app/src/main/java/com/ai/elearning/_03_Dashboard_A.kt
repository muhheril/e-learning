package com.ai.elearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class _03_Dashboard_A : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_03_dashboard)

        fragmentContainer = findViewById(R.id.fragment_container)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.home -> _04_Home_F()
                R.id.info -> _05_Info_F()
                R.id.pesan -> _06_Pesan_F()
                R.id.profil -> _07_Profil_F()
                else -> _04_Home_F() // Default fragment
            }

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (selectedFragment::class.java != currentFragment?.javaClass) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            }
            true
        }

        // Set default fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, _04_Home_F()).commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}
