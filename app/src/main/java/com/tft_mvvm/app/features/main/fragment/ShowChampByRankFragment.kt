package com.tft_mvvm.app.features.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.dialog_show_details_champ.DialogShowDetailsChamp
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampByRank
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_champ_by_rank.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByRankFragment : Fragment(),
    OnItemClickListener {
    private val showChampByRankViewModel: ShowChampByRankViewModel by viewModel()
    private var adapterShowChampByRank: AdapterShowChampByRank? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_champ_by_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        observeViewModel()
        showChampByRankViewModel.getChamps(true)
    }

    private fun setupUI() {
        swipeRefreshLayoutByRank?.setOnRefreshListener {
            showChampByRankViewModel.getChamps(true)
        }
        adapterShowChampByRank =
            AdapterShowChampByRank(
                arrayListOf(),
                this
            )
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

    private fun observeViewModel() {
        showChampByRankViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer {
                adapterShowChampByRank?.addData(it)
            })
        showChampByRankViewModel.isRefresh().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutByRank?.isRefreshing = it
        })
    }

    override fun onClickListener(id: String) {
        val dialog = DialogShowDetailsChamp.newInstance(id)
        dialog.show(childFragmentManager, "DialogShowDetailsChamp")
    }
}
