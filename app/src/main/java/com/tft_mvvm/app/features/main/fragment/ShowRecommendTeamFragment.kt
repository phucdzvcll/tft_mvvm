package com.tft_mvvm.app.features.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tft_mvvm.app.base.OnItemClickListener
import com.tft_mvvm.app.features.dialog_show_details_champ.DialogShowDetailsChamp
import com.tft_mvvm.app.features.main.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.app.features.main.viewmodel.ShowTeamRecommendViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_recommend_team.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShowRecommendTeamFragment : Fragment(),
    OnItemClickListener {
    private val showTeamRecommendViewModel: ShowTeamRecommendViewModel by viewModel()
    private var adapterShowRecommendTeamBuilder: AdapterShowRecommendTeamBuilder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_recommend_team, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
        observeViewModel()
        showTeamRecommendViewModel.getListTeamBuilder(false)
    }

    private fun setupUi() {

        rv_by_team_recommend?.layoutManager = LinearLayoutManager(requireContext())
        adapterShowRecommendTeamBuilder =
            AdapterShowRecommendTeamBuilder(
                arrayListOf(),
                this
            )
        rv_by_team_recommend?.adapter = adapterShowRecommendTeamBuilder

        swipeRefreshLayoutTeamBuilderRecommend?.setOnRefreshListener {
            showTeamRecommendViewModel.getListTeamBuilder(true)
        }
    }

    private fun observeViewModel() {
        showTeamRecommendViewModel.getListTeamBuilderLiveData()
            .observe(viewLifecycleOwner, Observer {
                adapterShowRecommendTeamBuilder?.addData(it)
            })
        showTeamRecommendViewModel.isRefresh().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutTeamBuilderRecommend?.isRefreshing = it
        })
    }

    override fun onClickListener(id: String) {
        val dialog = DialogShowDetailsChamp.newInstance(id)
        dialog.show(childFragmentManager, "DialogShowDetailsChamp")
    }


}