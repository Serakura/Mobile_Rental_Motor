package com.example.tbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MotorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motor)

        val merek = findViewById<TextView>(R.id.motor_merk)
        val nopol = findViewById<TextView>(R.id.motor_nopol)
        val tarif = findViewById<TextView>(R.id.motor_tarif)
        val warna = findViewById<TextView>(R.id.motor_warna)
        val tahun = findViewById<TextView>(R.id.motor_thn)
        val gambar = findViewById<ImageView>(R.id.motor_gambar)
        val jenis = findViewById<TextView>(R.id.motor_jenis)

        val mrk = intent.getStringExtra("merek")
        val npl = intent.getStringExtra("nopol")
        val trf = intent.getStringExtra("tarif")
        val wrn = intent.getStringExtra("warna")
        val thn = intent.getStringExtra("tahun")
        val gmbr = intent.getStringExtra("gambar")
        val jns = intent.getStringExtra("jenis")

        merek.text = mrk
        nopol.text = npl
        jenis.text = jns
        tarif.text = trf
        warna.text = wrn
        tahun.text = thn
            Glide.with(this).load(gmbr).thumbnail(0.5f).diskCacheStrategy(
                DiskCacheStrategy.ALL).into(gambar)

        val btn_back = findViewById<TextView>(R.id.btn_kembali)
        btn_back.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val btn_pesan = findViewById<Button>(R.id.btn_booking)
        btn_pesan.setOnClickListener{
            val a = findViewById<TextView>(R.id.motor_tarif).text
            val b = findViewById<TextView>(R.id.motor_nopol).text
            val intent = Intent(this,BookingActivity::class.java)
            intent.putExtra("nopol",b)
            intent.putExtra("tarif",a)
            startActivity(intent)
        }
    }
}