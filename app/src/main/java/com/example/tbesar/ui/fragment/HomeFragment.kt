package com.example.distrodeku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.tbesar.MotorActivity
import com.example.tbesar.R
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.motor.Motor
import com.example.tbesar.model.motor.motorAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var motorArrayList : ArrayList<Motor>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val listView : ListView = view.findViewById(R.id.listView)


        RetrofitClient.instance.tampilMotor().enqueue(object : Callback<List<Motor>>{
            override fun onResponse(call: Call<List<Motor>>, response: Response<List<Motor>>) {
                if(response.body() != null && response.isSuccessful()) {
                    motorArrayList = ArrayList()
                    for (i in response.body()!!.indices){
                        val mobel = Motor(response.body()!![i]!!.nama_kendaraan,response.body()!![i]!!.plat_nomor,response.body()!![i]!!.warna,response.body()!![i]!!.tahun_pembuatan,response.body()!![i]!!.tarif,response.body()!![i]!!.gambar,response.body()!![i]!!.jenis)
                        motorArrayList.add(mobel)
                    }
                    listView.adapter = motorAdapter(requireActivity(),motorArrayList)
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val mrk = response.body()!![position]!!.nama_kendaraan
                        val plt = response.body()!![position]!!.plat_nomor
                        val traf = response.body()!![position]!!.tarif
                        val wrn = response.body()!![position]!!.warna
                        val th = response.body()!![position]!!.tahun_pembuatan
                        val gmbr = response.body()!![position]!!.gambar
                        val jns = response.body()!![position]!!.jenis

                        val i = Intent(activity,MotorActivity::class.java)
                        i.putExtra("merek",mrk)
                        i.putExtra("nopol",plt)
                        i.putExtra("tarif",traf)
                        i.putExtra("warna",wrn)
                        i.putExtra("tahun",th)
                        i.putExtra("gambar",gmbr)
                        i.putExtra("jenis",jns)
                        startActivity(i)
                    }


                }
            }

            override fun onFailure(call: Call<List<Motor>>, t: Throwable) {
                Toast.makeText(requireActivity().applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

        })





        val bFilter : TextView = view.findViewById(R.id.tv_filter)
        bFilter.setOnClickListener{
            Toast.makeText(requireActivity().applicationContext,"Maaf, Masih Tahap Pengembangan!",Toast.LENGTH_LONG).show()
        }

        val search : EditText = view.findViewById(R.id.et_search)

        return view
    }
}