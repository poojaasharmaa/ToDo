package com.example.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class ToDo {

    @PrimaryKey
    var id=0

    @SerializedName("userId")
    var userId=0

    @SerializedName("title")
    var title=""

    @SerializedName("completed")
    var completed=false
}