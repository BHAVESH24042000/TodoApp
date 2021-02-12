package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

lateinit var viewColorTag:View
lateinit var txtShowTitle:TextView
lateinit var txtShowTask:TextView
lateinit var txtShowCategory:TextView
lateinit var txtShowDate:TextView
lateinit var txtShowTime: TextView

class TodoAdapter(val list:List<TodoModel>): RecyclerView.Adapter<TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
      holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

}



class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{


    fun bind(todoModel: TodoModel) {
        with(itemView){

            viewColorTag=findViewById(R.id.viewColorTag)
            txtShowTitle=findViewById(R.id.txtShowTitle)
            txtShowTask=findViewById(R.id.txtShowTask)
            txtShowCategory=findViewById(R.id.txtShowCategory)
            txtShowDate=findViewById(R.id.txtShowDate)
            txtShowTime=findViewById(R.id.txtShowTime)

            val colors=resources.getIntArray(R.array.random_color)
            val randomColor=colors[Random().nextInt(colors.size)]
            viewColorTag.setBackgroundColor(randomColor)
             txtShowTitle.text=todoModel.title
            txtShowTask.text=todoModel.description
            txtShowCategory.text=todoModel.category
            updateTime(todoModel.time)
            updateDate(todoModel.date)


        }

    }

    fun updateTime(time: Long) {
        //Mon, 5 Jan 2020
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        txtShowTime.text = sdf.format(Date(time))

    }

    fun updateDate(time: Long) {
        //Mon, 5 Jan 2020
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
        txtShowDate.text = sdf.format(Date(time))

    }
}

