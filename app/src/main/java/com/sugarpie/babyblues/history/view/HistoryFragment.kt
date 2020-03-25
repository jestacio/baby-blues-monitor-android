package com.sugarpie.babyblues.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.history.logic.HistoryListAdapter
import com.sugarpie.babyblues.history.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var rvAdapter: HistoryListAdapter
    private lateinit var rvLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)

        rvAdapter = HistoryListAdapter(listOf("No Results"))
        rvLayoutManager = LinearLayoutManager(context)

        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = rvLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        // move observer to controller class if it gets more complicated
        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        historyViewModel.filenames.observe(this, Observer {
            Log.d(TAG, "historyViewModel.filenames changed")
            rvAdapter.updateList(it)
        })

        context?.let { historyViewModel.scanFiles(it) }

        return root
    }

    companion object {
        const val TAG = "HistoryFragment"
    }
}
