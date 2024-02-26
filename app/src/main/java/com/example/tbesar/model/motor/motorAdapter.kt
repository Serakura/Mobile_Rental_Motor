package com.example.tbesar.model.motor

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

class motorAdapter(private val context : Activity, private val arrayList : ArrayList<Motor>) : ArrayAdapter<Motor>(context, R.layout.list_item,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item,null)

        val imageView : ImageView = view.findViewById(R.id.moto_pict)
        val merk : TextView = view.findViewById(R.id.moto_merk)
        val nopol : TextView = view.findViewById(R.id.moto_plat)
        val tarif : TextView = view.findViewById(R.id.moto_tarif)

        Glide.with(context).load(arrayList[position].gambar).thumbnail(0.5f).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(imageView)


        merk.text = arrayList[position].nama_kendaraan
        nopol.text = arrayList[position].plat_nomor
        tarif.text = arrayList[position].tarif


        return view
    }
}