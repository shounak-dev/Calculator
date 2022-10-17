package com.shounakcalculator.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shounakcalculator.calculator.R
import com.shounakcalculator.calculator.db.Calculation

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var listOfEquations: List<Calculation> = emptyList()

    override fun getItemCount() = listOfEquations.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.data = listOfEquations[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(parent)
    }

    fun setData(listIn: List<Calculation>) {
        listOfEquations = listIn
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.element_history_item, parent, false
        )
    ) {
        var data: Calculation? = null
            set(value) {
                field = value
                setupUI()
            }

        private fun setupUI() {
            data?.let { calc ->
                itemView.findViewById<TextView>(R.id.history_equation).text = calc.equation
                itemView.findViewById<TextView>(R.id.history_solution).text = "= ${calc.answer}"
            }
        }

    }

}