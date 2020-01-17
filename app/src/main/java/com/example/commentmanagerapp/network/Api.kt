package com.example.commentmanagerapp.network

import com.example.commentmanagerapp.model.Comment
import com.example.commentmanagerapp.model.Posts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("posts")
    fun getPosts(): Call<List<Posts>>

    @GET("posts/{id}")
    fun getPostsData(@Path("id") id: Int): Call<Posts>

    @GET("comments?")
    fun getComment(@Query("postId") postId: Int): Call<List<Comment>>

}

