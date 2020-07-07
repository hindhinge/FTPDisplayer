package com.rsawicki.ftp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsawicki.ftp.Connect
import kotlinx.android.synthetic.main.begin.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.begin)

        begin_begin.setOnClickListener {
            startActivity(Intent(this,Connect::class.java))
        }

    }
}
