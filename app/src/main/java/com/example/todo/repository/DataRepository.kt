package com.example.todo.repository

import com.example.todo.network.Api
import com.example.todo.network.RetrofitInstance


class DataRepository {
      var instance: DataRepository? = null
      fun repositoryInstance():DataRepository{
            if (instance==null){
                instance= DataRepository()
            }
            return instance as DataRepository
      }
      suspend fun getToDoData()= RetrofitInstance.getInstance().create(Api::class.java).getToDoData()
}