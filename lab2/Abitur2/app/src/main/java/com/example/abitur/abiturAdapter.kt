package com.example.abitur

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView

class abiturAdapter(var mContext: Context, textViewReourseId:Int):
ArrayAdapter<Abitur>(mContext, textViewReourseId, abiturs)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var grid: View

        if (convertView == null) {
            grid = View(mContext)
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            grid = inflater.inflate(
                R.layout.cellgrid,
                null,
                false
            )
        } else {
            grid = convertView
        }

        setupTextView(grid, R.id.surname, abiturs[position].surname)
        setupTextView(grid, R.id.name, abiturs[position].name)
        setupTextView(grid, R.id.middlename, abiturs[position].middlename)

        var marksWithS = abiturs[position].marks.toString()
        var marks = marksWithS.substring(1, marksWithS.length-1)

        setupTextView(grid, R.id.mark, marks)


        if (greaterThanAverage(abiturs[position]))
            grid.setBackgroundColor(Color.MAGENTA)

        return grid
    }

    private fun setupTextView(grid: View, viewId: Int, str: kotlin.String) {


        val view  = grid.findViewById<TextView>(viewId)
        view.text = str
    }

    private fun setupTextColor(grid: View, viewId: Int, str: kotlin.String) {
        val view  = grid.findViewById<TextView>(R.id.surname)
        view.text = str
    }

    // возвращает содержимое выделенного элемента списка
    override fun getItem(position: Int): Abitur {
        return abiturs[position]
    }

    fun addItem(abitur: Abitur) {
        abiturs.add(abitur)
        Sort()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        abiturs.removeAt(position)
        Sort()
        notifyDataSetChanged()
    }

    fun getAll() : List<Abitur> {
        return abiturs
    }

    fun getAverageScore():Double {

        if(abiturs.size == 0)
            return 0.0

        var sum = 0
        var count = 0
        for (i in abiturs) {
            for (j in i.marks) {
                sum += j
                count += 1
            }
        }
        return (sum.toDouble() / count.toDouble())
    }

    fun greaterThanAverage(abitur: Abitur): Boolean {
        var sum = 0
        for (j in abitur.marks) {
            sum += j
        }

        var average = sum.toFloat()/abitur.marks.size.toFloat()

        if(average > getAverageScore())
            return true

        return false
    }

    companion object {
        private var abiturs= mutableListOf<Abitur>()
    }

    fun Sort(){
        abiturs.sortWith(AbiturComparator())

    }

}

class AbiturComparator : Comparator<Abitur> {
    override fun compare(p0: Abitur?, p1: Abitur?): Int {
        if(p0 == null || p1 == null)
            return 0
        return getMark(p0).compareTo(getMark(p1))

    }
    private fun getMark(it: Abitur): Float{
        var lhs: Float = it.marks.sum().toFloat()
        var rhs: Float = it.marks.size.toFloat()
        return -(lhs / rhs)
    }
}