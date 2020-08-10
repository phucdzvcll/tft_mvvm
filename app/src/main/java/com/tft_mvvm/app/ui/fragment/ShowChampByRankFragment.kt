package com.tft_mvvm.app.ui.fragment

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
import com.tft_mvvm.app.ui.adapter.AdapterShowChampByRank
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_champ_by_rank.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByRankFragment : Fragment(), OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private var adapterShowChampByRank: AdapterShowChampByRank? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_champ_by_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getChamp(false)
        setupUI()
    }

    private fun setupUI() {
        swipeRefreshLayoutByRank?.setOnRefreshListener {
            getChamp(true)
        }
        adapterShowChampByRank = AdapterShowChampByRank(arrayListOf(), this)
        val mGridLayoutManager = GridLayoutManager(requireContext(), 6)
        mGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapterShowChampByRank?.getItemViewType(position) == adapterShowChampByRank?.HEADER_TYPE) {
                    6
                } else {
                    1
                }
            }

        }
        rv_by_rank?.layoutManager = mGridLayoutManager
        rv_by_rank?.adapter = adapterShowChampByRank
    }

    private fun getChamp(isForceLoadData:Boolean) {
        mainViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer {
                adapterShowChampByRank?.addData(it.sortedByDescending { champ -> champ.rankChamp })
            })
        mainViewModel.isRefresh().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutByRank?.isRefreshing = it
        })
        mainViewModel.getChamps(isForceLoadData)
    }

    override fun onClickListener(champ: Champ) {
        val dialog = com.tft_mvvm.app.ui.Dialog(requireContext(), champ)
        dialog.show()
    }
}
