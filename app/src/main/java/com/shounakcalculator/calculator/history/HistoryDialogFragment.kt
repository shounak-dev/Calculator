package com.shounakcalculator.calculator.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shounakcalculator.calculator.CalculatorViewModel
import com.shounakcalculator.calculator.R

class HistoryDialogFragment : DialogFragment() {

    private val historyAdapter = HistoryAdapter()

    private val viewModel: CalculatorViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_history, container, false)
        v.findViewById<RecyclerView>(R.id.history_list).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = historyAdapter
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lastCalculations.observe(this) {
            historyAdapter.setData(it)
        }
    }

}