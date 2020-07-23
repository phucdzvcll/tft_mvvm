package com.tft_mvvm.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tft_mvvm.tft_mvm.R
import com.tft_mvvm.data.Champ


var mPosts: MutableList<com.tft_mvvm.data.Champ> = mutableListOf()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
