package com.example.tbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isEmpty
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.register.register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val et_ktp = findViewById<EditText>(R.id.txt_reg_ktp)
        val et_name = findViewById<EditText>(R.id.txt_reg_name)
        val et_phone = findViewById<EditText>(R.id.txt_reg_phone)
        val et_username = findViewById<EditText>(R.id.txt_reg_username)
        val et_password = findViewById<EditText>(R.id.txt_reg_password)
        val rg_jenkel = findViewById<RadioGroup>(R.id.jenkel_Grup)
        val et_alamat = findViewById<EditText>(R.id.txt_reg_alamat)

        val buttonRegister = findViewById<Button>(R.id.button_register)
        buttonRegister.setOnClickListener{
            val ktp =et_ktp.text.toString().trim()
            val nama = et_name.text.toString().trim()
            val telp = et_phone.text.toString().trim()
            val alamat = et_alamat.text.toString().trim()
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()
            val jk = rg_jenkel.checkedRadioButtonId
            val jkl = findViewById<RadioButton>(jk)
            val jenkel = jkl.text.toString().trim()

            if(nama.isEmpty()){
                et_name.error="Name required"
                et_name.requestFocus()
                return@setOnClickListener
            }
            if(ktp.isEmpty()){
                et_ktp.error="Nomor KTP required"
                et_ktp.requestFocus()
                return@setOnClickListener
            }
            if(telp.isEmpty()){
                et_phone.error="Number Phone required"
                et_phone.requestFocus()
                return@setOnClickListener
            }
            if(alamat.isEmpty()){
                et_alamat.error="Alamat required"
                et_alamat.requestFocus()
                return@setOnClickListener
            }
            if(rg_jenkel.checkedRadioButtonId == -1){
                jkl.setError("Select Item!")
            }
            if(username.isEmpty()){
                et_username.error="Username required"
                et_username.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                et_password.error="Password required"
                et_password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(ktp,nama, telp,jenkel, alamat, username, password)
                .enqueue(object: Callback<register>{
                override fun onResponse(call: Call<register>, response: Response<register>) {
                    if(response.body() != null && response.isSuccessful() && response.body()?.result_code == true){
                    Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                    val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                    startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<register>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })

        }

    }


}