package com.tft_mvvm.app.features.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.details.DetailsChampActivity
import com.tft_mvvm.app.features.main.adapter.AdapterShowByGold
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByGoldViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_champ_by_gold.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByGoldFragment : Fragment(),
    OnItemClickListener {
    private val showChampByGoldViewModel: ShowChampByGoldViewModel by viewModel()
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
        observerViewModel()

        showChampByGoldViewModel.getChamps(true)
    }

    private fun observerViewModel() {
        showChampByGoldViewModel.getChampsLiveData().observe(viewLifecycleOwner, Observer {
            adapter?.addData(it)
        })
        showChampByGoldViewModel.isRefresh().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutByGold?.isRefreshing = it
        })
    }

    private fun setupUi() {
        rvByGold?.layoutManager = GridLayoutManager(this.requireContext(), 4)
        adapter = AdapterShowByGold(
            arrayListOf(),
            this
        )
        rvByGold?.adapter = adapter
        swipeRefreshLayoutByGold?.setOnRefreshListener {
            showChampByGoldViewModel.getChamps(true)
        }
    }


    override fun onClickListener(id: String) {
        startActivity(DetailsChampActivity.newIntent(requireContext(), id))
    }

}