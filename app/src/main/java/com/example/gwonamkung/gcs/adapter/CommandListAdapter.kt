package com.example.gwonamkung.gcs.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gwonamkung.gcs.R
import kotlinx.android.synthetic.main.list_layout.view.*

class CommandListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var view : View? = null
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        var view = p0 as ViewHolder
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