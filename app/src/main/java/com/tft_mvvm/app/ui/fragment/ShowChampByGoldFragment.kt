package com.tft_mvvm.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.activity.DetailsChampActivity
import com.tft_mvvm.app.ui.adapter.AdapterShowByGold
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.activity_details_champ.*
import kotlinx.android.synthetic.main.fragment_show_champ_by_gold.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByGoldFragment : Fragment(), OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private var adapter: AdapterShowByGold? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_champ_by_gold, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        true.observerViewModel()
        getChampForShowByGold()
    }

    private fun getChampForShowByGold() {
        mainViewModel.getChampsLiveData().observe(viewLifecycleOwner, Observer {
            adapter?.addData(it)
        })
    }

    private fun Boolean.observerViewModel() {
        mainViewModel.getChamps(this)
    }

    private fun setupUi() {
        rvByGold?.layoutManager = GridLayoutManager(this.requireContext(), 4)
        adapter = AdapterShowByGold(arrayListOf(), this)
        rvByGold?.adapter = adapter
        swipeRefreshLayoutByGold?.setOnRefreshListener {
            mainViewModel.isRefresh().observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutByGold?.isRefreshing = it
            })
            true.observerViewModel()
        }
    }


    override fun onClickListener(id: String) {
        startActivity(DetailsChampActivity.newIntent(requireContext(), id))
    }

}