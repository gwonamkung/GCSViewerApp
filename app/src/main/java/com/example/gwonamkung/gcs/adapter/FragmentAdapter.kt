package com.example.gwonamkung.gcs.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.gwonamkung.gcs.fragment.Fragment1
import com.example.gwonamkung.gcs.fragment.Fragment2

class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var fragments = arrayOf(Fragment1(),Fragment2())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragments[p0]
    }

    fun setJson(json : String){

    }
}