package com.dronefive.gwonamkung.gcs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dronefive.gwonamkung.gcs.R
import kotlinx.android.synthetic.main.fragment1.view.*
import org.json.JSONObject

class Fragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment1, container, false)
        mode_text = view.mode_text
        alt_text = view.alt_text
        aspeed_text = view.aspeed_text
        gspeed_text = view.gspeed_text
        time_text = view.time_text
        battery_text = view.battery_text
        return view
    }

    companion object {
        var mode_text : TextView? = null
        var alt_text : TextView? = null
        var aspeed_text : TextView? = null
        var gspeed_text : TextView? = null
        var time_text : TextView? = null
        var battery_text : TextView? = null
        fun json(str : JSONObject){
            try {
                mode_text!!.text = str.getString("mode")
                alt_text!!.text = "" + str.getDouble("altitude")
                aspeed_text!!.text = ("" + str.getDouble("airspeed")).substring(0,6)
                gspeed_text!!.text = ("" + str.getDouble("groundspeed")).substring(0,6)
                battery_text!!.text = str.getDouble("battery_voltage").toString()
                time_text!!.text = "time_init.."
                battery_text!!.text = str.getDouble("battery_voltage").toString()
            }catch (e: Exception){}
        }
    }
}