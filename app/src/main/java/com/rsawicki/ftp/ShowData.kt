package com.rsawicki.ftp

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.begin.*
import kotlinx.android.synthetic.main.showdata.*

class ShowData : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showdata)
        showBack.setOnClickListener {
            startActivity(Intent(this,Connect::class.java))
        }

        val lookup = arrayOf<String>(
            "NOVAL","ESC","1","2","3","4","5","6","7","8", //0-9
            "9","0","-","=","BACKSPACE","TAB","q","w","e","r", //10-19
            "t","y","u","i","o","p","[","]","RETURN","CTRLLEFT", //20-29
            "a","s","d","f","g","h","j","k","l",";", //30-39
            "'","`","SHIFTLEFT","\\","z","x","c","v","b","n", //40-49
            "m",",",".","/","SHIFTRIGHT","*","ALTLEFT","SPACE","CAPSLOCK","F1", //50-59
            "F2","F3","F4","F5","F6","F7","F8","F9","F10","NUMLOCK", //60-69 nice
            "SCROLLOCK","7","8","9","-","4","5","6","+","1", //70-79
            "2","3","0",".","NOVAL","NOVAL","INTERNATIONAL","F11","F12","NOVAL", //80-89
            "NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","RETURN","CTRLRIGHT","/","PRINTSCRN", //90-99
            "ALTRIGHT","NOVAL","HOME","UP","PAGEUP","LEFT","RIGHT","END","DOWN","PAGEDOWN", //100-109
            "INSERT","DELETE","NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","PAUSE", //110-119
            "NOVAL","NOVAL","NOVAL","NOVAL","NOVAL","LOGOLEFT","LOGORIGHT","NOVAL","NOVAL","NOVAL" //120-129
        )
        val keypresses:MutableList<String> = ArrayList()
        val output:MutableList<String> = ArrayList()

        val data = intent.getStringExtra("data")
//        val nonewlines = data.replace("\n"," ")
        var splitstring = data.lines()
        var arrlength = 0
        for (code in splitstring){
            if(code == ""){
                continue
            } else {
                val index = code.toInt()
                keypresses.add(lookup[index])
                arrlength += 1

            }
        }
        var index = 0
        var caps = 0
        for (i in 0..arrlength-1) {
            val key = keypresses[i]
            if (key == "BACKSPACE"){
                if(index == 0){
                    continue
                } else{
                    output.removeAt(index-1)
                    index -= 1
                }
            } else if (key == "DELETE"){
                if (index == output.size-1){
                    continue
                } else {
                    output.removeAt(index - 1)
                }
            } else if(key == "LEFT"){
                if(index == 0){
                    continue
                } else {
                    index -= 1
                }
            } else if(key == "RIGHT") {
                if (index == output.size-1) {
                    continue
                } else {
                    index +=1
                }
            }else if(key == "NOVAL") {
                continue
            }else if(key == "HOME") {
                index = 0
            }else if(key == "END") {
                index = output.size
            }else if(key == "SPACE") {
                output.add(index," ")
                index+=1
            }else if(key == "RETURN") {
                output.add(index,"\n")
                index+=1
            }else if(key == "CAPSLOCK") {
                if (caps == 1){
                    caps = 0
                } else {
                    caps = 1
                }
            }
            else {
                if (caps == 1){
                    output.add(index,key.toUpperCase())
                    index+=1
                } else {
                    output.add(index,key)
                    index += 1
                }

            }
            println("key:"+key+"     index:"+index+"     i:"+i+"           "+output.joinToString(separator = ""))


        }
        showOutput.text = output.joinToString(separator = "")

    }
}