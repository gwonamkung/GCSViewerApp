package com.example.gwonamkung.gcs

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener {
            var intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
            startActivityForResult(intent,111)
        }
    }
}