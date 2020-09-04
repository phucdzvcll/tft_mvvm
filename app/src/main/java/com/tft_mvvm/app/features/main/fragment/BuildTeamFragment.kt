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
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.app.features.main.adapter.AdapterShowByGold
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampByOriginAndClass
import com.tft_mvvm.app.features.main.model.Champ
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_build_team.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuildTeamFragment : Fragment(),
    OnItemClickListener {
    private val showChampByRankViewModel: ShowChampByRankViewModel by viewModel()
    private var adapterShowChampByOriginAndClass: AdapterShowChampByOriginAndClass? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_build_team, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        observeViewModel()
        test()
        showChampByRankViewModel.getAllClassAndOriginName()
    }

    private fun setupUI() {
        adapterShowChampByOriginAndClass =
            AdapterShowChampByOriginAndClass(
                this
            )
        val mGridLayoutManager = GridLayoutManager(requireContext(), 8)
        mGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapterShowChampByOriginAndClass?.getItemViewType(position) == adapterShowChampByOriginAndClass?.HEADER_TYPE) {
                    8
                } else {
                    1
                }
            }

        }
        rv_by_rank?.layoutManager = mGridLayoutManager
        rv_by_rank?.adapter = adapterShowChampByOriginAndClass
    }

    private fun observeViewModel() {
        showChampByRankViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer {
                adapterShowChampByOriginAndClass?.setData(it)
            })
    }

    private fun test() {
        val listChamp = mutableListOf<Champ>().apply {
            repeat(10) {
                add(
                    Champ(
                        id = "",
                        rank = "",
                        name = "",
                        cost = "",
                        imgUrl = ""
                    )
                )
            }
        }
        val adapterShowChampByGold = AdapterShowByGold(listChamp,this)
        rv_pick_champ?.layoutManager = GridLayoutManager(requireContext(),5)
        rv_pick_champ?.adapter=adapterShowChampByGold
    }

    override fun onClickListener(id: String) {
        val dialog = DialogShowDetailsChamp.newInstance(id)
        dialog.show(childFragmentManager, "DialogShowDetailsChamp")
    }

    override fun onClickListenerForChampInTeam(
        id: String,
        listItem: List<ChampDialogModel.Item>,
        star: String
    ) {
    }
}
