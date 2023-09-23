package com.ai.elearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference()

//        val currentDate = Date()
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy   |   HH:mm")
//        val formattedDate = dateFormat.format(currentDate)
        //TambahPengumuman("Arnita Irianti, S.Si., M.Si.","$formattedDate","DISAMPAIKAN KEPADA MAHASISWA AGAR MENGIRIMKAN DRAFT(DOC/DOCX/PDF), PRESENTASI (PPT/PPTX), SEBAGAI SYARAT ACC UNTUK UJIAN PADA TANGGAL YANG TELAH DIPILIH SEBELUMNYA. APABILA TELAH DISETUJUI JUGA WAJIB MENGIRIMKAN DRAFT MELALUI WHATSAPP KEPADA PEMBIMBING/PENGUJI PALING LAMBAT 3 HARI SEBELUM PELAKSANAAN UJIAN.")
        //TambahPengumuman("Arnita Irianti, S.Si., M.Si.","$formattedDate","BAGI MAHASISWA YANG AKAN UJIAN HASIL DAN UJIAN TUTUP AGAR MELAPOR KEBAGIAN AKADEMIK FAKULTAS DENGAN MEMBAWA FOTOCOPY IJAZAH SMA/SEDERAJAT UNTUK PENGURUSAN PENERBITAN IZIN UJIAN TUTUP DARI AKADEMIK REKTORAT.")
        //TambahPengumuman("Arnita Irianti, S.Si., M.Si.","$formattedDate","DISAMPAIKAN KEPADA MAHASISWA AGAR SEGERA MENYETOR TUGAS FLOWCHARTNYA SEBELUM UAS.")


//        ubahRPS("http://ti.handayani.ac.id/wp-content/uploads/2020/07/RPS-E-COMMERCE.pdf")
//
//        TambahModulPerkuliahan("Jaringan Komputer","Praktikum","https://drive.google.com/file/d/1Bqpi2H4aSgKnGRpFKuvCCNKdsV0ejSoA/view")
//        TambahModulPerkuliahan("Jaringan Komputer","Teori","https://drive.google.com/file/d/1EUGj91XsTfM0JqqQB9oI1wncnIuMWpB_/view")
//        TambahModulPerkuliahan("Teknologi Informasi","Praktikum","https://drive.google.com/file/d/1son29l8s4VSzdsL7RZ2JqaQjWpnuYXGr/view")
//        TambahModulPerkuliahan("Pengantar Teknologi Informasi","Teori","https://drive.google.com/file/d/1lcDw74TFCss9381Am2669qSoFTcVFuFl/view")
//        TambahModulPerkuliahan("Sistem Operasi","Praktikum","https://drive.google.com/file/d/1CAHL4MHXDi9fNfdc4uLlu8JV-GOiBTUB/view")
//        TambahModulPerkuliahan("Sistem Operasi","Teori","https://drive.google.com/file/d/1S1Yu5YDx_RgXnpMocO2r0yZ8fKM0DgMF/view")
//        TambahModulPerkuliahan("Komputer Grafis","Praktikum","https://drive.google.com/file/d/11N4ZOOJ_JnrFoVrEJz_ZxyTgH-O_ZhRt/view")
//        TambahModulPerkuliahan("Analisis dan Perancangan Sistem Informasi","Teori dan Praktikum","https://d3mi.amikom.ac.id/media/02/MODUL_ANSI.pdf")
//        TambahModulPerkuliahan("Perancangan Basis Data","Teori dan Praktikum","https://d3mi.amikom.ac.id/media/02/Modul_PBD_d3MI.pdf")
//        TambahModulPerkuliahan("Multimedia","Praktikum","https://drive.google.com/file/d/1T8Txy36NhzpKKfplK-Id_PPwnxaOX-oR/view")
//
//        TambahMateriMataKuliah("Materi 1","Algoritma Pemrograman","https://drive.google.com/file/d/1LMixdwcQz0wLKEvHgIj7H6Fbgar-8lDs/view")
//        TambahMateriMataKuliah("Materi 2","Flowchart","https://drive.google.com/file/d/1NdEd3qROpclK83c_xvNv9aZ05Dyae_Yo/view")
//        TambahMateriMataKuliah("Materi 3","Perangkat Lunak, Instalasi dan Menguji","https://drive.google.com/file/d/1gnAgKi1-ZXDuIOPThm1iHcyC_wBxw_rg/view")
//        TambahMateriMataKuliah("Materi 4","Editor, lingkungan kerja, Struktur program dan Menguji kode program bahasa pemrograman komputer","https://drive.google.com/file/d/1n7bDDWlTuDIO5EU3G4Z_cacuyL3i2TNF/view")
//        TambahMateriMataKuliah("Materi 5","Tipe data, Variabel, Konstanta, Operator, Ekspresi","https://drive.google.com/file/d/15pRx6LDRNz6IWjqJHkkC_pPZgCkswqRt/view")
//        TambahMateriMataKuliah("Materi 6","Operator aritmatika, Operator logika, Operasi aritmatika dan Operasi Logika","https://drive.google.com/file/d/1uFDF21Z4wus-hZtjhWm_K4-8bgo4dn48/view")
//        TambahMateriMataKuliah("Materi 7","Percabangan","https://drive.google.com/file/d/1-hv5oKfyQJpluumSLBfnSM0EdGO84hP0/view")
//        TambahMateriMataKuliah("Materi 8","Perulangan (Looping)","https://drive.google.com/file/d/1merSTX7-z1A6A2C6WHR_yfnPbr9ff4PT/view")
//        TambahMateriMataKuliah("Materi 9","Array","https://drive.google.com/file/d/1vdbytOpiaEhUzNnsCgLkMzrcOGYmfsj1/view")
//
//        TambahVideo("Belajar Kotlin Dasar - 1 Pengenalan Kotlin","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/6dSNbskzlz4?si=YHWEN-ffyU07I7cv\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 2 Program Hello World","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/DhRIBJ6q8Ks?si=jjc0UVx65f1rNZ_W\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 3 Tipe Data Number","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/d_ip0wTcKvw?si=dV6LQofIP4kM_-Kc\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 4 Tipe Data Character","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/F1UqaaiPnJM?si=Bo0hLN3B44uBZwz2\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 5 Tipe Data Boolean","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/kNCsS40aCv8?si=X3aRe0Tp_KNZ_phE\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 6 Tipe Data String","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/dY60nHPbATs?si=rFIwxwSe_aH0VeIu\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 7 Variable","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/BPQWkU5MF7Q?si=zMI5sFTMyZdEbM1P\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 8 Tipe Data Array","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/080BcEOhWe0?si=2l-q7bGDd5rP-vLF\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 9 Tipe Data Range","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/UJpYyOqbzaU?si=Ulp7_dmN9NutuV83\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//        TambahVideo("Belajar Kotlin Dasar - 10 Operasi Matematika","Belajar Kotlin Dasar untuk Pemula","<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/NV0aNm_U3BE?si=3IZFQ3lzOsvawpE0\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>")
//

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)


        Handler().postDelayed({
            if (isLoggedIn) {
                val intent = Intent(this, _03_Dashboard_A::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, _01_Login_A::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000) // Menunda selama 1 detik (1000 milidetik)


    }

    private fun ubahRPS(link:String) {
        usersRef.child("rps").setValue(link)
    }
    private fun TambahModulPerkuliahan(judul:String,deskripsi:String,link:String) {
        val tambahmodulperkuliahan = HashMap<String, Any>()
        tambahmodulperkuliahan["judul_modul"] = judul
        tambahmodulperkuliahan["deskripsi_modul"] = deskripsi
        tambahmodulperkuliahan["link_modul"] = link
        usersRef.child("modul").push().setValue(tambahmodulperkuliahan)
    }
    private fun TambahMateriMataKuliah(judul:String,deskripsi:String,link:String) {
        val tambahmaterimatakuliah = HashMap<String, Any>()
        tambahmaterimatakuliah["judul_materi"] = judul
        tambahmaterimatakuliah["deskripsi_materi"] = deskripsi
        tambahmaterimatakuliah["link_materi"] = link
        usersRef.child("materi").push().setValue(tambahmaterimatakuliah)
    }
    private fun TambahVideo(judul:String,deskripsi:String,link:String) {
        val tambahvideo = HashMap<String, Any>()
        tambahvideo["judul_video"] = judul
        tambahvideo["deskripsi_video"] = deskripsi
        tambahvideo["link_video"] = link
        usersRef.child("video").push().setValue(tambahvideo)
    }

    private fun TambahPengumuman(pengirim:String,waktu:String,pesan:String) {
        val tambahpengumuman = HashMap<String, Any>()
        tambahpengumuman["i_pengirim"] = pengirim
        tambahpengumuman["i_waktu"] = waktu
        tambahpengumuman["i_pesan"] = pesan
        usersRef.child("pengumuman").push().setValue(tambahpengumuman)
    }

    private fun TambahChat(pengirim:String,waktu:String,pesan:String) {
        val tambahchat = HashMap<String, Any>()
        tambahchat["c_pengirim"] = pengirim
        tambahchat["c_waktu"] = waktu
        tambahchat["c_pesan"] = pesan
        usersRef.child("pesan").push().setValue(tambahchat)
    }


}
