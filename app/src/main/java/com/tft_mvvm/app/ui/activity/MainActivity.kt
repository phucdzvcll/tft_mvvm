package com.tft_mvvm.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
    OnItemClickListener {
    private val champViewModel: MainViewModel by viewModel()
    private lateinit var adapter: MyAdapter
    private var isLoading: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        getChamps()
        sort()

    }

    private fun setupUI() {
        adapter = MyAdapter(arrayListOf(),this)
        rv.layoutManager = GridLayoutManager(this, 4)
        rv.adapter = adapter
        swipe_refresh_layout.setOnRefreshListener {
            getChamps()
            group_radio_btn.clearCheck()
        }
    }

    private fun sort() {
        btn_sort_by_name.setOnClickListener {
            if(isLoading==false){
                adapter.sortByName()
            }
        }
        btn_sort_by_coat.setOnClickListener {
            if(isLoading==false){
                adapter.sortByCoat()
            }
        }
    }

    private fun getChamps() {
        champViewModel.getChamps()
        champViewModel.getChampsLiveData()
            .observe(this, Observer {
                adapter.addData(it)
            })
        champViewModel.isRefresh()
            .observe(this, Observer {swipe_refresh_layout.isRefreshing = it })
    }

    override fun onClickListener(champ: Champ) {
        val intent = Intent(this,
            DetailsChamp::class.java)
            intent.putExtra("champ",champ)
        startActivity(intent)
    }
}