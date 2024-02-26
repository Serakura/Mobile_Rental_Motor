package com.example.tbesar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.distrodeku.fragment.HomeFragment
import com.example.distrodeku.fragment.akunFragment
import com.example.distrodeku.fragment.keranjangFragment
import com.example.tbesar.databinding.ActivityMainBinding
import com.example.tbesar.storage.SharedPrefManager

class MainActivity : AppCompatActivity() {

    val fragmentHome : Fragment = HomeFragment()
    val fragmentAkun : Fragment = akunFragment()
    val fragmentKeranjang : Fragment = keranjangFragment()
    val fm : FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome

    private lateinit var menu : Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView
    private  lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setUpBottomNav()





    }

    fun setUpBottomNav(){
        val data = SharedPrefManager.getInstance(this).user
        val bundle = Bundle()
        bundle.putString("nama", data.nama.toString())
        bundle.putString("ktp", data.nomor_ktp.toString())
        bundle.putString("telp", data.telp.toString())
        bundle.putString("jenkel", data.jenkel.toString())
        bundle.putString("alamat", data.alamat.toString())
        fragmentAkun.arguments = bundle




        fm.beginTransaction().add(R.id.fragmentContainer, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.fragmentContainer, fragmentAkun).hide(fragmentAkun).commit()
        fm.beginTransaction().add(R.id.fragmentContainer, fragmentKeranjang).hide(fragmentKeranjang).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    callFragment(0,fragmentHome)
                }
                R.id.navigation_keranjang -> {
                    callFragment(1,fragmentKeranjang)
                }
                R.id.navigation_akun -> {
                    callFragment(2,fragmentAkun)
                }
            }
            false
        }

    }

    fun callFragment(int: Int , fragment: Fragment){

        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}