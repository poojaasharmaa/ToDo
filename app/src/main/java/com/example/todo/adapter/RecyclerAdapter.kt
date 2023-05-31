package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.AppDatabase
import com.example.todo.model.ToDo
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(var list:ArrayList<ToDo>): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
    return list.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data:ToDo){
            val spStatus=itemView.findViewById<Spinner>(R.id.spStatus)
            val tvTitle=itemView.findViewById<TextView>(R.id.tvTitle)
            tvTitle.text=data.title
            val list=ArrayList<String>()
            list.add("Pending")
            list.add("Completed")


            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spStatus.adapter = adapter
            val status = if (data.completed){
                spStatus.setSelection(1)
                1
            }else{
                spStatus.setSelection(0)
                0
            }
            spStatus?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                      if (status!=position){
                          if (position==0)
                          AppDatabase.getMainThread(itemView.context)?.toDoDao()?.updateStatusByUserId(data.id,false)
                          else
                          AppDatabase.getMainThread(itemView.context)?.toDoDao()?.updateStatusByUserId(data.id,true)
                          }
                }

            }
        }
    }

}