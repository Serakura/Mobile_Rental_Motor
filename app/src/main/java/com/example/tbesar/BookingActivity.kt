package com.example.tbesar

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.TextWatcher
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.motor.pinjamMotor
import com.example.tbesar.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BookingActivity : AppCompatActivity() {
    private lateinit var tvDate : TextView
    private  lateinit var datePickerButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        tvDate = findViewById<TextView>(R.id.tv_date)
        datePickerButton = findViewById<Button>(R.id.datePickerButton)
        
        val myCalendar = Calendar.getInstance()
        
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateAble(myCalendar)
        }

        datePickerButton.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }



        val btn_back = findViewById<TextView>(R.id.btn_balik)
        btn_back.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val ktp = findViewById<TextView>(R.id.tv_ktp)
        val nopol = findViewById<TextView>(R.id.tv_nopol)
        val durasi = findViewById<EditText>(R.id.et_durasi)
        val cal = findViewById<Button>(R.id.datePickerButton)
        val bayar = findViewById<TextView>(R.id.tv_tbayar)

        ktp.text = SharedPrefManager.getInstance(this).user.nomor_ktp
        val tarif = intent.getStringExtra("tarif")
        nopol.text = intent.getStringExtra("nopol")



        durasi.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(durasi.text.toString() == ""){
                    durasi.error="Tidak Boleh Konsong!"
                    durasi.requestFocus()
                    bayar.text = "0"
                } else {
                    val a = durasi.text.toString().toInt() * tarif.toString().toInt()
                    val tbayar = findViewById<TextView>(R.id.tv_tbayar)
                    tbayar.text = a.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        val btn_pesan = findViewById<Button>(R.id.btn_pesan)
        btn_pesan.setOnClickListener{
            val id = ktp.text.toString().trim()
            val npl = nopol.text.toString().trim()
            val drs = durasi.text.toString().trim()
            val tgl = tvDate.text.toString().trim()
            val tb = findViewById<TextView>(R.id.tv_tbayar).text.toString().trim()

            if (tgl.isEmpty()){
                tvDate.error="Silahkan Pilih Tanggal Booking!"
                cal.requestFocus()
                return@setOnClickListener
            }
            if (drs.isEmpty()){
                durasi.error="Durasi Tidak Boleh Kosong!"
                durasi.requestFocus()
                return@setOnClickListener
            }
            if (tb.isEmpty()){
                bayar.error="Durasi Tidak Boleh Kosong!"
                bayar.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.pinjamMotor(id,npl,tgl,drs,tb).enqueue(object:
                Callback<pinjamMotor>{
                override fun onResponse(call: Call<pinjamMotor>, response: Response<pinjamMotor>) {
                    if(response.body() != null && response.isSuccessful() && response.body()?.result_code == true){
                        Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                        val intent = Intent(this@BookingActivity,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<pinjamMotor>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })

        }


    }

    private fun updateAble(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDate.setText(sdf.format(myCalendar.time))

    }
}