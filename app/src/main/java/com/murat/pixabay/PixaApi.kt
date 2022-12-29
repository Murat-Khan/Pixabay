package com.murat.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {
    @GET("api/")
    fun searchImage(
        @Query("q") keyWord :String,
        @Query("page") page :Int ,
        @Query("per_page") per_page :Int=5,
        @Query("key") key: String = "32403222-eefbe2e9ae046c6127288b03e"
    ): Call<PixaModel>
}