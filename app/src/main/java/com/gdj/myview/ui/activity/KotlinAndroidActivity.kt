package com.gdj.myview.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter

import com.gdj.myview.R
import kotlinx.android.synthetic.main.activity_kotlin_android.*
//import kotlinx.android.synthetic.main.scrollView.*

class KotlinAndroidActivity : AppCompatActivity() , View.OnTouchListener{

    // val mAdapter:ArrayAdapter<String>(this,R.layout.item_main_list,getDatas())
   // mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getDatas());
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_android)

        listView.isFocusable=false


    }

}
