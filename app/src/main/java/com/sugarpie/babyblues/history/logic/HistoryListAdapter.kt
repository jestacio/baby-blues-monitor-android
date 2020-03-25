package com.sugarpie.babyblues.history.logic

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sugarpie.babyblues.Log
import com.sugarpie.babyblues.R
import com.sugarpie.babyblues.SharedExtras
import com.sugarpie.babyblues.Utils
import com.sugarpie.babyblues.epds.view.EPDSResultActivity
import com.sugarpie.babyblues.epds.viewmodel.EPDSAssessmentViewModel
import java.io.File
import java.lang.StringBuilder

class HistoryListAdapter(initialList: List<String>) :
    RecyclerView.Adapter<HistoryListAdapter.ListItemViewHolder>() {

    class ListItemViewHolder(inflatedView: View) : RecyclerView.ViewHolder(inflatedView) {
        val textView: TextView = inflatedView.findViewById(R.id.textview)
        var filename: String? = null
    }

    private val filenames = mutableListOf<String>()

    init {
        updateList(initialList)
    }

    fun updateList(newList: List<String>) {
        Log.d(TAG, "updateList list had ${filenames.size} items")

        filenames.clear()
        filenames.addAll(newList)
        notifyDataSetChanged()

        Log.d(TAG, "updateList list now has ${filenames.size} items")
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${filenames.size} items")
        return filenames.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder ${position}: ${filenames[position]}")
        holder.textView.text = Utils.prettyTimestamp(filenames[position])
        holder.filename = filenames[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        Log.d(TAG, "onCreateViewHolder")
        val viewHolder = ListItemViewHolder(itemView)
        val act = Utils.getActivityFromView(parent)

        itemView.setOnClickListener {
            Log.d(TAG, "itemView clicked, filename=${viewHolder.filename}")

            if (act == null) {
                Log.w(TAG, "itemView clicked, filename=${viewHolder.filename}, null activity")
                return@setOnClickListener
            }

            val epdsResultsDir = Utils.getEPDSResultsDir(parent.context)
            val bufferedReader = File(epdsResultsDir.absolutePath + "/"
                    + viewHolder.filename).bufferedReader()
            val epdsJson = bufferedReader.use { it.readText() }

            // convert to view model instance from json
            val epdsAssessmentViewModel = EPDSAssessmentViewModel.fromJsonStr(epdsJson)

            val intent = Intent().apply {
                setClass(act.applicationContext, EPDSResultActivity::class.java)
                putExtra(SharedExtras.EXTRA_COMPLETED_EPDS_TEXT, epdsAssessmentViewModel.toText())
                putExtra(SharedExtras.EXTRA_SCORE, epdsAssessmentViewModel.getScore().value)
            }

            act.startActivity(intent)
        }

        return viewHolder
    }

    companion object {
        const val TAG = "HistoryListAdapter"
    }
}