package com.example.prep

import retrofit2.Call
import retrofit2.http.*
interface APIInterface {
    @GET("/celebrities/")
    fun getCelebrities(): Call<ArrayList<Celebrity>>


    @POST("/celebrities/")
    fun addcelebrity(@Body userData: Celebrity): Call<Celebrity>

    @GET("/celebrities/{id}")
    fun getCelebrity(@Path("id") id: Int): Call<Celebrity>

    @PUT("/celebrities/{id}")
    fun updatecelebrity(@Path("id") id:Int, @Body userData: Celebrity): Call<Celebrity>



    @DELETE ("/celebrities/{id}")
    fun deletecelebrity(@Path("id") id : Int ) :Call<Void>

}