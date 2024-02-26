package com.example.distrodeku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tbesar.LoginActivity
import com.example.tbesar.MainActivity
import com.example.tbesar.R
import com.example.tbesar.storage.SharedPrefManager


class akunFragment : Fragment() {
    lateinit var s:SharedPrefManager
    lateinit var btnLogout:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        val tvNama : TextView = view.findViewById(R.id.akun_nama)
        val tvKtp : TextView = view.findViewById(R.id.akun_ktp)
        val tvTelp : TextView = view.findViewById(R.id.akun_telp)
        val tvJenkel : TextView = view.findViewById(R.id.akun_jenkel)
        val tvAlamat : TextView = view.findViewById(R.id.akun_alamat)

        val args = this.arguments

        val namaUser = args?.get("nama")
        val ktpUser = args?.get("ktp")
        val telpUser = args?.get("telp")
        val jenkelUser = args?.get("jenkel")
        val alamatUser = args?.get("alamat")

        tvNama.text = namaUser.toString()
        tvKtp.text = ktpUser.toString()
        tvTelp.text = telpUser.toString()
        tvJenkel.text = jenkelUser.toString()
        tvAlamat.text = alamatUser.toString()


        s = SharedPrefManager.getInstance(requireActivity())

        btnLogout = view.findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener(){
            s.clear()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }



    return view
    }

}


