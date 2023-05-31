package com.example.todo.network

import com.example.todo.model.ToDo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("todos")
    suspend fun getToDoData(): Response<ArrayList<ToDo>>
}