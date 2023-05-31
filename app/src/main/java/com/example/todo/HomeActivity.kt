package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.RecyclerAdapter
import com.example.todo.database.AppDatabase
import com.example.todo.model.ToDo
import com.example.todo.viewModel.ToDoVwModel

class HomeActivity : AppCompatActivity() {
    lateinit var viewModel: ToDoVwModel
    lateinit var usersList:ArrayList<Int>
    lateinit var spUsers:Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerVw:RecyclerView
    lateinit var adapter:RecyclerAdapter
    lateinit var dataList:ArrayList<ToDo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initi()
        if(AppDatabase.getMainThread(this)?.toDoDao()?.getUsersList()!!.isEmpty()){
            progressBar.visibility=View.VISIBLE
            viewModel.fetchData()
            viewModel.getDataList().observe(this, Observer {
                if (it!=null) {
                    viewModel.insertData(it,this)
                    getUsersList()
                    progressBar.visibility=View.GONE
                }
            })
            return
        }

        getUsersList()
    }
    private fun getUsersList(){
        viewModel.getUsersList(this).observe(this, Observer {
            if (it!=null){
                usersList=it
                setSpinner()
            }
        })
    }

    private fun setSpinner(){
        val adap = ArrayAdapter(this, android.R.layout.simple_spinner_item, usersList)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUsers.adapter = adap
        spUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getDataByUserId(this@HomeActivity, spUsers.selectedItem as Int).observe(this@HomeActivity, Observer {
                    if (it!=null){
                        adapter=RecyclerAdapter(it)
                        recyclerVw.adapter=adapter
                    }
                })
            }

        }
    }
    private fun initi(){
        spUsers=findViewById(R.id.spId)
        progressBar=findViewById(R.id.progressBar)
        recyclerVw=findViewById(R.id.recyclerVw)
        usersList=ArrayList()
        dataList=ArrayList()
        viewModel=ViewModelProvider(this)[ToDoVwModel::class.java]
        recyclerVw.layoutManager=LinearLayoutManager(this)
        adapter=RecyclerAdapter(list = dataList)
        recyclerVw.adapter=adapter
    }
}