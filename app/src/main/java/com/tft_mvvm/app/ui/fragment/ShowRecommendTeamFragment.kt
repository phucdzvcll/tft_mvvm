package com.tft_mvvm.app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tft_mvvm.app.features.champ.viewmodel.TeamRecommendViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.AdapterShowRecommendTeamBuilder
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_show_recommend_team.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShowRecommendTeamFragment : Fragment(), OnItemClickListener {
    private val teamRecommendViewModel: TeamRecommendViewModel by viewModel()
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
        getTeams(false)
    }

    private fun setupUi() {

        rv_by_team_recommend?.layoutManager = LinearLayoutManager(requireContext())
        adapterShowRecommendTeamBuilder = AdapterShowRecommendTeamBuilder(arrayListOf(), this)
        rv_by_team_recommend?.adapter = adapterShowRecommendTeamBuilder
        swipeRefreshLayoutTeamBuilderRecommend?.setOnRefreshListener {
            getTeams(true)
        }
    }

    private fun getTeams(isForceLoadData: Boolean) {
        teamRecommendViewModel.getListTeamBuilderLiveData()
            .observe(viewLifecycleOwner, Observer {
                Log.d("PHUC", "Observer -> $it")
                it.sortedBy { teamBuilder -> teamBuilder.name }
                adapterShowRecommendTeamBuilder?.addData(it)
            })
        teamRecommendViewModel.isLoading()
            .observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutTeamBuilderRecommend?.isRefreshing = it
            })
        teamRecommendViewModel.getTeams(isForceLoadData)
    }

    override fun onClickListener(id: String) {
    }


}