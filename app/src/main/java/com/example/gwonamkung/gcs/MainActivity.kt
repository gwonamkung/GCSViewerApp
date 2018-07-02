package com.example.gwonamkung.gcs

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.gwonamkung.gcs.adapter.FragmentAdapterJ
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        tabLayout.addTab(tabLayout.newTab().setText("1번탭"))
        tabLayout.addTab(tabLayout.newTab().setText("2번탭"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "${p0!!.position + 1} 선택됨", Toast.LENGTH_SHORT).show()
                viewPager.currentItem = p0!!.position
            }
        })

        viewPager.adapter = FragmentAdapterJ(supportFragmentManager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }
}
