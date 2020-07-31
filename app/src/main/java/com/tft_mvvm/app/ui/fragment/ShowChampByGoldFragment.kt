package com.tft_mvvm.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.activity.DetailsChampActivity
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.champ.R
import com.tft_mvvm.champ.databinding.FragmentShowChampByGoldBinding
import kotlinx.android.synthetic.main.fragment_show_champ_by_gold.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByGoldFragment : Fragment(), OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private var adapter: MyAdapter? = null

    var biding: FragmentShowChampByGoldBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        biding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_show_champ_by_gold,
            container,
            false
        )
        return biding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getChamp()
        setupUi()
    }

    private fun setupUi() {
        adapter = MyAdapter(arrayListOf(), this)
        biding?.rvByGold?.layoutManager = GridLayoutManager(this.requireContext(), 4)
        biding?.rvByGold?.adapter = adapter
        biding?.swipeRefreshLayoutByGold?.setOnRefreshListener {
            getChamp()
        }
    }

    private fun getChamp() {
        mainViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer { adapter!!.addData(it) })
        mainViewModel.isRefresh().observe(
            viewLifecycleOwner,
            Observer { biding?.swipeRefreshLayoutByGold?.isRefreshing = it })
        mainViewModel.getChamps()
    }

    override fun onClickListener(champ: Champ) {
        startActivity(DetailsChampActivity.newIntent(requireContext(), champ))
    }

}