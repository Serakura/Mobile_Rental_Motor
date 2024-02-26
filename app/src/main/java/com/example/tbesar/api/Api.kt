package com.example.tbesar.api

import com.example.tbesar.model.login.login
import com.example.tbesar.model.motor.Motor
import com.example.tbesar.model.motor.pinjamMotor
import com.example.tbesar.model.pinjam.tampilPinjam
import com.example.tbesar.model.register.register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("register.php")
    fun createUser(
        @Field("nomor_ktp") ktp:String,
        @Field("nama_customer") nama:String,
        @Field("no_telp") nohp:String,
        @Field("jenkel") jenkel:String,
        @Field("alamat") alamat:String,
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<register>

    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<login>

    @GET("tampilMotor.php")
    fun tampilMotor() :Call<List<Motor>>

    @FormUrlEncoded
    @POST("pinjamMotor.php")
    fun pinjamMotor(
        @Field("nomor_ktp") ktp: String,
        @Field("plat_nomor") nopol: String,
        @Field("tanggal") tanggal: String,
        @Field("durasi_sewa") durasi: String,
        @Field("total_bayar") bayar: String
    ):Call<pinjamMotor>

    @FormUrlEncoded
    @POST("tampilPinjam.php")
    fun tampilPinjam(
        @Field("nomor_ktp") ktp: String
    ): Call<List<tampilPinjam>>
}