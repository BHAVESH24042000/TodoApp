package com.example.todoapp

//import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


const val DB_NAME="todo.db"


class TaskActivity : AppCompatActivity(), View.OnClickListener
{
    lateinit var myCalendar: Calendar
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    private val labels = arrayListOf("Personal", "Business", "Insurance", "Shopping", "Banking", "Other")

    var finalDate=0L
    var finalTime=0L

    val db by lazy{

        AppDatabase.getDatabase(this)

    }

    lateinit var dateEdt: TextInputEditText
    lateinit var timeInptLay: TextInputLayout
    lateinit var timeEdt: TextInputEditText
    lateinit var spinnerCategory:Spinner
    lateinit var saveBtn:  MaterialButton
    lateinit var taskInpLay:TextInputLayout
    lateinit var titleInpLay:TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        dateEdt= findViewById(R.id. dateEdt)
        timeInptLay=findViewById(R.id.timeInptLay)
         timeEdt=findViewById(R.id.timeEdt)
        spinnerCategory=findViewById((R.id.spinnerCategory))
       taskInpLay=findViewById(R.id.taskInpLay)
       titleInpLay=findViewById(R.id.titleInpLay)
        saveBtn=findViewById(R.id.saveBtn)

        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
        setUpSpinner()
    }


    override fun onClick(v: View) {

            when(v.id) {
                R.id.dateEdt->
                {
                    setDateListener()
                }
                R.id.timeEdt ->
                {
                    setTimeListener()
                }

                R.id.saveBtn -> {
                    saveTodo()
                }
            }

    }

    private fun setDateListener()
    {
        myCalendar= Calendar.getInstance()

        dateSetListener=DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        val datePickerDialog = DatePickerDialog(
                this, dateSetListener, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

   // @SuppressLint("SimpleDateFormat")
    private fun updateDate() {
        //Mon, 5 Jan 2020
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
       finalDate = myCalendar.time.time
        dateEdt.setText(sdf.format(myCalendar.time))

        timeInptLay.visibility = View.VISIBLE
    }
    private fun setTimeListener() {
        myCalendar= Calendar.getInstance()

        timeSetListener =
                TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    myCalendar.set(Calendar.MINUTE, min)
                    updateTime()
                }

        val timePickerDialog = TimePickerDialog(
                this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun updateTime()
    {
        //Mon, 5 Jan 2020
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        finalTime = myCalendar.time.time
        timeEdt.setText(sdf.format(myCalendar.time))

    }

    private fun setUpSpinner() {
        val adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels)

        labels.sort()

        spinnerCategory.adapter = adapter
    }

    private fun saveTodo() {
        val category = spinnerCategory.selectedItem.toString()
        val title = titleInpLay.editText?.text.toString()
        val description = taskInpLay.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTask(
                        TodoModel(
                                title,
                                description,
                                category,
                                finalDate ,
                                finalTime
                        )
                )
            }
            finish()
        }

    }
}