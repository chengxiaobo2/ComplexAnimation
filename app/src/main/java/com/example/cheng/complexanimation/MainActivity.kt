package com.example.cheng.complexanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cheng.complexanimation.util.dp2Px
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resouceList = arrayOf(R.drawable.p_1, R.drawable.p_2, R.drawable.p_3, R.drawable.p_4, R.drawable.p_5)
        val list = ArrayList<ImageView>()
        for (i in 0..4) {
            val iv = ImageView(this)
            iv.setImageResource(resouceList[i])
            iv.layoutParams = ViewGroup.LayoutParams(dp2Px(this, 40.0f), dp2Px(this, 40.0f))
            list.add(iv)
        }
        navigationBarView.addImageButtons(list)

    }
}
