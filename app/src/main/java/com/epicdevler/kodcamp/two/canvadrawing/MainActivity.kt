package com.epicdevler.kodcamp.two.canvadrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import com.epicdevler.kodcamp.two.canvadrawing.views.CanvaView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val canva = CanvaView(this)
        canva.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canva.contentDescription = "Hello"
        setContentView(canva)
    }
}