package com.tft_mvvm.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.activity.DetailsChamp
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.champ.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByGold : Fragment(), OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private var adapter: MyAdapter?=null
    private var rv_by_gold:RecyclerView?=null
    private var swipeRefreshLayout : SwipeRefreshLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_champ_by_gold, container, false)
        rv_by_gold = view.findViewById(R.id.rv_by_gold)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_by_gold)
        getChamp()
        setupUi()
        return view
    }

    private fun setupUi() {
        adapter = MyAdapter(arrayListOf(), this)
        rv_by_gold!!.layoutManager = GridLayoutManager(this.requireContext(), 4)
        rv_by_gold!!.adapter = adapter
        swipeRefreshLayout!!.setOnClickListener {
            swipeRefreshLayout!!.isRefreshing=true
        }
    }

    private fun getChamp() {
        mainViewModel.getChamps()
        mainViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer { adapter!!.addData(it) })
        mainViewModel.getIsRefresh().observe(viewLifecycleOwner, Observer { swipeRefreshLayout!!.isRefreshing = it })
    }

    override fun onClickListener(champ: Champ) {
        val intent = Intent(this.activity, DetailsChamp::class.java)
        intent.putExtra("champ", champ)
        startActivity(intent)
    }

}