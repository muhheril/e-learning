package com.ai.elearning

import android.Manifest
import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class _07_Profil_F : Fragment() {

    private lateinit var tvNama: TextView
    private lateinit var tvNIM: TextView
    private lateinit var tvKelas: TextView
    private lateinit var tvJurusan: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var tvNoHP: TextView
    private lateinit var tvEmail: TextView
    private lateinit var containerr: ConstraintLayout
    private lateinit var loading: LottieAnimationView
    private lateinit var loadingProfil: LottieAnimationView
    private lateinit var profilSaya: ImageView
    private lateinit var gantiprofil: CardView

    private lateinit var lNIM: LinearLayout
    private lateinit var lKelas: LinearLayout
    private lateinit var lJurusan: LinearLayout
    private lateinit var lAlamat: LinearLayout
    private lateinit var lNoHP: LinearLayout
    private lateinit var lgantipassword: LinearLayout

    private lateinit var logout: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var snama = ""
    private var snim = ""
    private var skelas = ""
    private var sjurusan = ""
    private var salamat = ""
    private var snohp = ""
    private var semail = ""
    private var sprofil = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment__07__profil_, container, false)

        tvNama = view.findViewById(R.id.tv_namaku_profil_id)
        tvNIM = view.findViewById(R.id.tv_nim_id)
        tvKelas = view.findViewById(R.id.tv_kelas_id)
        tvJurusan = view.findViewById(R.id.tv_jurusan_id)
        tvAlamat = view.findViewById(R.id.tv_alamatsaya_id)
        tvNoHP = view.findViewById(R.id.tv_nohp_id)
        tvEmail = view.findViewById(R.id.editTextEmail)
        containerr = view.findViewById(R.id.containerprofil)
        loading = view.findViewById(R.id.loadingprofil)
        logout = view.findViewById(R.id.btn_logout_id)
        loadingProfil=view.findViewById(R.id.loading_item_id)
        profilSaya =view.findViewById(R.id.img_profilku_profil_id)
        gantiprofil = view.findViewById(R.id.ganti_profil_id)

        lNIM = view.findViewById(R.id.profilku_nim_saya_id)
        lKelas = view.findViewById(R.id.profilku_kelas_saya_id)
        lJurusan = view.findViewById(R.id.profilku_jurusan_saya_id)
        lAlamat = view.findViewById(R.id.profilku_alamat_saya_id)
        lNoHP = view.findViewById(R.id.profilku_nohp_saya_id)
        lgantipassword = view.findViewById(R.id.profilku_gantipassword_saya_id)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            containerr.visibility=View.GONE
            loading.visibility=View.VISIBLE
            mulai(view)
        }
        containerr.visibility=View.GONE
        loading.visibility=View.VISIBLE
        mulai(view)

        logout.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.umum_dialog_permission)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val tv_judul : TextView = dialog.findViewById(R.id.tv_dialog_judul_id)
            val animasi : LottieAnimationView = dialog.findViewById(R.id.animasi_dialog_id)
            val btn_izinkan : Button = dialog.findViewById(R.id.tombol_izinkan_dialog_id)

            tv_judul.setText("Apakah anda yakin keluar akun ?")
            animasi.setAnimation(R.raw.logout)
            val layoutParams = animasi.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.height= 300
            layoutParams.topMargin = 30
            layoutParams.bottomMargin = 30
            animasi.layoutParams =layoutParams
            btn_izinkan.setText("Ya")
            btn_izinkan.setOnClickListener(View.OnClickListener {
                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                dialog.setCancelable(false)
                tv_judul.setText("Keluar akun berhasil")
                btn_izinkan.isEnabled=false
                animasi.setAnimation(R.raw.check)
                animasi.setRepeatCount(0)
                animasi.playAnimation()
                animasi.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {}
                    override fun onAnimationEnd(p0: Animator) {
                        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", false)
                        editor.apply()
                        auth.signOut()
                        val intent = Intent(requireContext(), _01_Login_A::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    override fun onAnimationCancel(p0: Animator) {}
                    override fun onAnimationRepeat(p0: Animator) {}
                })
            })
            dialog.show()
        }

        return view
    }

    private fun mulai(view: View) {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid
        usersRef = database.getReference("mahasiswa").child(userId.toString())

        if (userId!=null) {
            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(Model_Data::class.java)
                        userData?.let {
                            snama = it.nama.toString()
                            snim = it.nim.toString()
                            skelas = "Kelas ${it.kelas}"
                            sjurusan = it.jurusan.toString()
                            salamat = it.alamat.toString()
                            snohp = it.nohp.toString()
                            semail = it.email.toString()
                            sprofil = it.profil.toString()

                            tvNama.text = snama
                            tvNIM.text = snim
                            tvKelas.text = skelas
                            tvJurusan.text = sjurusan
                            tvAlamat.text = salamat
                            tvNoHP.text = snohp
                            tvEmail.text = semail

                            if(sprofil!= "-"){
                                Glide.with(requireActivity())
                                    .load(sprofil)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .listener(object : RequestListener<Drawable> {
                                        override fun onLoadFailed(
                                            e: GlideException?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            loadingProfil.visibility=View.GONE
                                            return true
                                        }

                                        override fun onResourceReady(
                                            resource: Drawable?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            dataSource: DataSource?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            loadingProfil.visibility=View.GONE
                                            profilSaya.setOnClickListener {
                                                val dialog = Umum_FullscreenImageDialog(requireContext(), sprofil)
                                                dialog.show()
                                            }
                                            return false
                                        }

                                    }).into(profilSaya)
                            }else{
                                loadingProfil.visibility = View.GONE
                                profilSaya.setImageResource(R.drawable.baseline_person_24)
                                profilSaya.setColorFilter(R.color.tombolnomor_transparant)
                            }

                            loading.visibility=View.GONE
                            containerr.visibility=View.VISIBLE
                            fungsikan(view)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    loading.visibility=View.GONE
                    containerr.visibility=View.VISIBLE
                }
            })
        }
    }

    private fun fungsikan(view: View) {

        gantiprofil.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 2)
            } else {
                dialogizin()
            }
        }
        tvNama.setOnClickListener {dialogdb(false,"Edit Nama","Masukkan nama anda",snama,"nama")}
        lNIM.setOnClickListener {dialogdb(false,"Edit NIM","Masukkan NIM anda",snim,"nim")}
        lKelas.setOnClickListener {dialogdb(true,"Edit Kelas","",skelas,"kelas")}
        lJurusan.setOnClickListener {dialogdb(true,"Edit Jurusan","",sjurusan,"jurusan")}
        lAlamat.setOnClickListener {dialogdb(false,"Edit Alamat","Masukkan alamat anda",salamat,"alamat")}
        lNoHP.setOnClickListener {dialogdb(false,"Edit Nomor Telepon","Masukkan nomor telepon anda",snohp,"nohp")}
        lgantipassword.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.umum_dialog_gantipassword)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val et_passwordlama : EditText = dialog.findViewById(R.id.et_passwordlama_id)
            val et_passwordbaru : EditText = dialog.findViewById(R.id.et_passwordbaru_id)
            val et_passwordkonfirmasipasswordbaru : EditText = dialog.findViewById(R.id.et_konfirmasipasswordbaru_id)
            val btn_simpan : Button = dialog.findViewById(R.id.tombol_simpan_dialog_id)
            val loading : LottieAnimationView = dialog.findViewById(R.id.animasi_dialog_id)
            val container: LinearLayout = dialog.findViewById(R.id.containergantipassword_id)

            btn_simpan.setOnClickListener(View.OnClickListener {
                val passwordLama = et_passwordlama.text.toString()
                val passwordBaru = et_passwordbaru.text.toString()
                val konfirmasiPasswordBaru = et_passwordkonfirmasipasswordbaru.text.toString()

                if (passwordLama.isNullOrEmpty()&&passwordBaru.isNullOrEmpty()&&konfirmasiPasswordBaru.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Lengkapi data terlebih dahulu.", Toast.LENGTH_SHORT).show()
                }else{
                    if (passwordBaru != konfirmasiPasswordBaru) {
                        Toast.makeText(requireContext(), "Password baru tidak cocok dengan konfirmasi password baru.", Toast.LENGTH_SHORT).show()
                        return@OnClickListener
                    }
                    loading.visibility = View.VISIBLE
                    container.visibility = View.GONE
                    val user = FirebaseAuth.getInstance().currentUser
                    val credential = EmailAuthProvider.getCredential(user?.email ?: "", passwordLama)

                    user?.reauthenticate(credential)?.addOnCompleteListener { reauthResult ->
                        if (reauthResult.isSuccessful) {
                            user.updatePassword(passwordBaru).addOnCompleteListener { updatePasswordResult ->
                                if (updatePasswordResult.isSuccessful) {
                                    Toast.makeText(requireContext(), "Password berhasil diubah.", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                } else {
                                    Toast.makeText(requireContext(), "Gagal mengubah password. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                                }
                                loading.visibility = View.GONE
                                container.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(requireContext(), "Autentikasi gagal. Pastikan password lama benar.", Toast.LENGTH_SHORT).show()
                            loading.visibility = View.GONE
                            container.visibility = View.VISIBLE
                        }
                    }
                }
            })
            dialog.show()
        }

    }

    private fun dialogizin() {
        val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.umum_dialog_permission)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val tv_judul : TextView = dialog.findViewById(R.id.tv_dialog_judul_id)
            val animasi : LottieAnimationView = dialog.findViewById(R.id.animasi_dialog_id)
            val btn_izinkan : Button = dialog.findViewById(R.id.tombol_izinkan_dialog_id)
            tv_judul.setText("Akses Galeri Diperlukan")
            val currentNightMode = AppCompatDelegate.getDefaultNightMode()
            animasi.setAnimation(if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) R.raw.izingalerynight else R.raw.izingaleryday)
            btn_izinkan.setOnClickListener(View.OnClickListener {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", requireContext().packageName, null)
                    startActivity(intent)
                }
                dialog.dismiss()
            })
            dialog.show()


    }

    private fun dialogdb(spinner:Boolean,tv_juduls:String,tv_hint:String,tv_objek:String,tv_child:String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val kelasOptions = arrayOf("A", "B", "C", "D", "E")
        val jurusanOptions = arrayOf("Teknik - Sipil", "Teknik - Informatika", "Teknik - Perencanaan Wilayah Kota")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val etnama : EditText

        if (spinner){
            val spinnerr: Spinner // Initialize to a default value
            dialog.setContentView(R.layout.umum_dialog_spinner) // Change to the appropriate layout containing the Spinner
            val tv_judul : TextView = dialog.findViewById(R.id.tv_dialog_judul_id)
            tv_judul.setText(tv_juduls)
            val btn_izinkan : Button = dialog.findViewById(R.id.tombol_izinkan_dialog_id)
            btn_izinkan.setText("SIMPAN")
            spinnerr = dialog.findViewById(R.id.spinner_id)
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, if (tv_child.equals("kelas")){kelasOptions}else{jurusanOptions})
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerr.adapter = spinnerAdapter
            btn_izinkan.setOnClickListener {
                val selectedItem = spinnerr.selectedItem.toString()
                if (selectedItem.isNotEmpty()) {
                    usersRef.child(tv_child).setValue(selectedItem).addOnSuccessListener {
                        if (tv_child == "kelas") {
                            skelas = selectedItem
                            tvKelas.text = skelas
                        } else {
                            sjurusan = selectedItem
                            tvJurusan.text = sjurusan
                        }
                        Toast.makeText(requireContext(), "Berhasil mengedit", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }.addOnFailureListener {
                        if (tv_child == "kelas") {
                            tvKelas.text = skelas
                        } else {
                            tvJurusan.text = sjurusan
                        }
                        Toast.makeText(requireContext(), "Gagal mengedit! Periksa koneksi internet anda", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } else {
                    Toast.makeText(requireContext(), "Pilih opsi yang valid!", Toast.LENGTH_SHORT).show()
                }
            }

        }
        else{
            dialog.setContentView(R.layout.umum_dialog_edittext)
            val tv_judul : TextView = dialog.findViewById(R.id.tv_dialog_judul_id)
            tv_judul.setText(tv_juduls)
            val btn_izinkan : Button = dialog.findViewById(R.id.tombol_izinkan_dialog_id)
            btn_izinkan.setText("SIMPAN")
            etnama = dialog.findViewById(R.id.et_nama_id)
            etnama.setHint(tv_hint)
            etnama.setText(tv_objek)
            when (tv_child) {
                "nama" -> {
                    etnama.inputType = InputType.TYPE_CLASS_TEXT
                    etnama.filters = arrayOf(InputFilter.LengthFilter(30))
                }
                "nim" -> {
                    etnama.inputType = InputType.TYPE_CLASS_TEXT
                    etnama.filters = arrayOf(InputFilter.LengthFilter(8))
                }
                "nohp" -> {
                    etnama.inputType = InputType.TYPE_CLASS_NUMBER
                    etnama.filters = arrayOf(InputFilter.LengthFilter(12))
                }
                "alamat" -> {
                    etnama.inputType = InputType.TYPE_CLASS_TEXT
                    etnama.filters = arrayOf(InputFilter.LengthFilter(50))
                }
            }

            btn_izinkan.setOnClickListener {
                val enteredText = etnama.text.toString()
                if (enteredText.isNotEmpty()) {
                    usersRef.child(tv_child).setValue(enteredText).addOnSuccessListener {
                        when (tv_child) {
                            "nama" -> {
                                snama = enteredText
                                tvNama.text = snama
                            }
                            "nim" -> {
                                snim = enteredText
                                tvNIM.text = snim
                            }
                            "nohp" -> {
                                snohp = enteredText
                                tvNoHP.text = snohp
                            }
                            "alamat" -> {
                                salamat = enteredText
                                tvAlamat.text = salamat
                            }
                        }
                        Toast.makeText(requireContext(), "Berhasil mengedit", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }.addOnFailureListener {
                        when (tv_child) {
                            "nama" -> tvNama.text = snama
                            "nim" -> tvNIM.text = snim
                            "nohp" -> tvNoHP.text = snohp
                            "alamat" -> tvAlamat.text = salamat
                        }
                        Toast.makeText(requireContext(), "Gagal mengedit! Periksa koneksi internet anda", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } else {
                    Toast.makeText(requireContext(), "Tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImageUri = data?.data
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            CropImage.activity(selectedImageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(requireContext(), this)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val croppedImageUri = result.uri
                compressAndUploadImage(croppedImageUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(requireContext(), "Terjadi kesalahan, periksa koneksi internet", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == 3) {
            // Setelah kembali dari pengaturan, periksa lagi izin
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 2)
            }
        }
    }

    private fun compressAndUploadImage(imageUri: Uri) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.umum_dialog_permission)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tv_judul: TextView = dialog.findViewById(R.id.tv_dialog_judul_id)
        val animasi: LottieAnimationView = dialog.findViewById(R.id.animasi_dialog_id)
        val btn_izinkan: Button = dialog.findViewById(R.id.tombol_izinkan_dialog_id)
        btn_izinkan.visibility = View.GONE
        tv_judul.text = "Sedang mengupload"
        animasi.setAnimation(R.raw.loadingitem)
        dialog.setCancelable(false)
        dialog.show()
        loadingProfil.visibility =View.VISIBLE

        val imageStream = requireContext().contentResolver.openInputStream(imageUri)
        val selectedBitmap = BitmapFactory.decodeStream(imageStream)

        // Kompresi gambar dengan kualitas yang lebih rendah
        val outputStream = ByteArrayOutputStream()
        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
        val compressedImageByteArray = outputStream.toByteArray()

        // Upload gambar yang telah dikompresi ke Firebase Storage
        val storageReference = FirebaseStorage.getInstance().reference
        val imagesReference = storageReference.child("profil")
        val imageRef = imagesReference.child("$snama.jpg")

        val uploadTask = imageRef.putBytes(compressedImageByteArray)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    sprofil = downloadUri.toString()

                    // Simpan URL gambar ke Firebase Realtime Database
                    usersRef.child("profil").setValue(sprofil)
                        .addOnSuccessListener {
                            dialog.dismiss()

                            Glide.with(requireContext())
                                .load(sprofil)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .listener(object : RequestListener<Drawable> {
                                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                        loadingProfil.visibility=View.GONE
                                        profilSaya.setOnClickListener {
                                            val dialog = Umum_FullscreenImageDialog(requireContext(), sprofil)
                                            dialog.show()
                                        }
                                        return false
                                    }

                                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                        loadingProfil.visibility=View.GONE
                                        return true
                                    }
                                }).into(profilSaya)
                            Toast.makeText(requireContext(), "Upload berhasil", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            dialog.dismiss()
                            loadingProfil.visibility=View.GONE
                            Toast.makeText(requireContext(), "Terjadi kesalahan, periksa koneksi internet", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                dialog.dismiss()
                loadingProfil.visibility=View.GONE
                Toast.makeText(requireContext(), "Terjadi kesalahan, periksa koneksi internet", Toast.LENGTH_SHORT).show()
            }
        }
    }





}