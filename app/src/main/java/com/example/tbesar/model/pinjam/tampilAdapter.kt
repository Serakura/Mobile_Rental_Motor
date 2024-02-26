package com.example.tbesar.model.pinjam

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tbesar.R

class   tampilAdapter(private val context : Activity, private val arrayList : ArrayList<tampilPinjam>) : ArrayAdapter<tampilPinjam>(context, R.layout.list_pinjam,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_pinjam,null)

        val imageView : ImageView = view.findViewById(R.id.pjm_pict)
        val merk : TextView = view.findViewById(R.id.pjm_merk)
        val nopol : TextView = view.findViewById(R.id.pjm_plat)
        val tanggal : TextView = view.findViewById(R.id.pjm_tanggal)
        val durasi : TextView = view.findViewById(R.id.pjm_durasi)
        val bayar : TextView = view.findViewById(R.id.pjm_tarif)

        Glide.with(context).load(arrayList[position].gambar).thumbnail(0.5f).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(imageView)


        merk.text = arrayList[position].nama_kendaraan
        nopol.text = arrayList[position].plat_nomor
        tanggal.text = arrayList[position].tanggal
        durasi.text = arrayList[position].durasi_sewa
        bayar.text = arrayList[position].total_bayar


        return view
    }
}