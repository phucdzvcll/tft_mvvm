package com.tft_mvvm.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.activity.DetailsChamp
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_champ_by_gold.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByGold : Fragment(),OnItemClickListener {

    private val champViewModel: MainViewModel by viewModel()
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupUI()
        return inflater.inflate(R.layout.fragment_show_champ_by_gold, container, false)
    }

    private fun setupUI() {
        adapter = MyAdapter(arrayListOf(),this)
        rv_by_gold.layoutManager = GridLayoutManager(activity, 4)
        rv_by_gold.adapter = adapter
        swipe_refresh_layout_by_gold.setOnRefreshListener {
            getChamps()
        }
    }

    private fun getChamps() {
        champViewModel.getChamps()

        activity?.let {
            champViewModel.getChampsLiveData()
                .observe(it, Observer {
                    adapter.addData(it)
                })
        }
        activity?.let {
            champViewModel.isRefresh()
                .observe(it, Observer {swipe_refresh_layout_by_gold.isRefreshing = it })
        }
    }

    override fun onClickListener(champ: Champ) {
        val intent = Intent(activity,
            DetailsChamp::class.java)
        intent.putExtra("champ",champ)
        startActivity(intent)
    }


}