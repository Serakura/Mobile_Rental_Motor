package com.example.distrodeku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tbesar.R
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.pinjam.tampilAdapter
import com.example.tbesar.model.pinjam.tampilPinjam
import com.example.tbesar.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class keranjangFragment : Fragment() {

    private lateinit var tampilArrayList : ArrayList<tampilPinjam>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        val listView : ListView = view.findViewById(R.id.list)
        val ktp = SharedPrefManager.getInstance(requireActivity()).user.nomor_ktp.toString()
        RetrofitClient.instance.tampilPinjam(ktp).enqueue(object : Callback<List<tampilPinjam>> {
            override fun onResponse(
                call: Call<List<tampilPinjam>>,
                response: Response<List<tampilPinjam>>
            ) {
                if(response.body() != null && response.isSuccessful()) {
                tampilArrayList = ArrayList()
                    for (i in response.body()!!.indices){
                        val pinjam = tampilPinjam(response.body()!![i]!!.nama_kendaraan,response.body()!![i]!!.plat_nomor,response.body()!![i]!!.tanggal,response.body()!![i]!!.durasi_sewa,response.body()!![i]!!.total_bayar,
                            response.body()!![i]!!.gambar)
                        tampilArrayList.add(pinjam)
                    }
                    listView.adapter = tampilAdapter(requireActivity(),tampilArrayList)
                } else {
                    Toast.makeText(requireActivity().applicationContext,"Tidak Ada Data Peminjaman!",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<tampilPinjam>>, t: Throwable) {
                Toast.makeText(requireActivity().applicationContext, t.message,Toast.LENGTH_SHORT).show()
            }
        })





        return view
    }


}