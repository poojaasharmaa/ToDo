package com.example.todo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.AppDatabase
import com.example.todo.model.ToDo
import com.example.todo.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoVwModel :ViewModel() {
    var data=MutableLiveData<ArrayList<ToDo>>()
    var dbData=MutableLiveData<ArrayList<ToDo>>()
    var usersList=MutableLiveData<ArrayList<Int>>()

    fun fetchData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                data.postValue(DataRepository().repositoryInstance().getToDoData().body())
            }
        }
    }

    fun getDataList():LiveData<ArrayList<ToDo>>{
        return data
    }

    fun insertData(list:ArrayList<ToDo>,context: Context)= AppDatabase.getMainThread(context)?.toDoDao()?.insertAll(list)

    fun getUsersList(context: Context) :LiveData<ArrayList<Int>>{
            usersList.postValue( AppDatabase.getMainThread(context)?.toDoDao()?.getUsersList() as ArrayList<Int>)
        return usersList
    }

    fun getDataByUserId(context: Context,userId:Int) :LiveData<ArrayList<ToDo>>{
        dbData.postValue( AppDatabase.getMainThread(context)?.toDoDao()?.getDataByUserId(userId) as ArrayList<ToDo>)
        return dbData
    }

}