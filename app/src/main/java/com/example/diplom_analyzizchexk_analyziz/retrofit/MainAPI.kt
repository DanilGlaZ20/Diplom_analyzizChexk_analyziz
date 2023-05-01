package com.example.diplom_analyzizchexk_analyziz.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainAPI
{
    @GET("getAll")
   suspend fun getProduct():List<Product>


   @POST("login")
   suspend fun auth(@Body login:Login): Response<Token>


}