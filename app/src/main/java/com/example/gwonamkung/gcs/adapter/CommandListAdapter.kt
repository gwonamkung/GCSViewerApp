package com.example.gwonamkung.gcs.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gwonamkung.gcs.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommandListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list : ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        var format : SimpleDateFormat
        format = SimpleDateFormat("hh : mm : ss")
        var view = p0 as ViewHolder
        view.time_text!!.text = format.format(Date().time)
        view.command_text!!.text = list.get(position)
    }

    class ViewHolder(view : View?) : RecyclerView.ViewHolder(view!!){
        var time_text : TextView? = null
        var command_text : TextView? = null
        init {
            time_text = view!!.findViewById(R.id.list_time_text)
            command_text = view!!.findViewById(R.id.list_text)
        }
    }
}