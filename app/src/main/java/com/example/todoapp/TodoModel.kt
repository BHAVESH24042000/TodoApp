package com.example.todoapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

//  https://developer.android.com/codelabs/android-room-with-a-view-kotlin#4  //
@Entity
data class TodoModel(
    var title:String,
    var description: String,
    var category:String,
    var date:Long,
    var time: Long,
    var isFinished: Int=0,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0

)

