package com.example.gwonamkung.gcs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.gwonamkung.gcs.R
import kotlinx.android.synthetic.main.fragment1.view.*

class Fragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment1, container, false)
        view!!.button!!.setOnClickListener {
            //  Toast.makeText(activity, "button clicked.", Toast.LENGTH_SHORT).show()
            var toast = Toast.makeText(activity, "button clicked.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }
        textView = view.textView
        return view
    }

    companion object {
        var textView:TextView? = null
        fun str(json : String) {
            try{textView!!.text = json}catch (e : Exception){}

        }
    }
}