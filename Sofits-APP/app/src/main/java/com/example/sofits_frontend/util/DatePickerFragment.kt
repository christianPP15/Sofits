package com.example.sofits_frontend.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    interface DatePickerListener {
        fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int)
    }
    lateinit var listener : DatePickerListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DatePickerListener
        } catch (e: Exception) {
            throw ClassCastException(activity.toString() + " Must Implements DatePickerListener")
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(),this,year,month,day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener.onDateSet(view,year,month,dayOfMonth)
    }
}