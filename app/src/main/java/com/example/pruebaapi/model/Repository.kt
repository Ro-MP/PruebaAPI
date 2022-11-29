package com.example.pruebaapi.model

import retrofit2.Call
import retrofit2.http.GET


interface Repository {

    @GET("posts")
    fun getPosts(): Call<List<Post>>
}