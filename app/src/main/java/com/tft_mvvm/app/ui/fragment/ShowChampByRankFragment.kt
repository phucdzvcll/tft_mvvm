package com.tft_mvvm.app.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.AdapterShowChampByRank
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.dialog_show_details_champ.*
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
        getChamp()
        setupUI()
    }

    private fun setupUI() {
        rv_by_rank?.layoutManager = GridLayoutManager(requireContext(), 5)
        adapterShowChampByRank = AdapterShowChampByRank(arrayListOf(), this)
        rv_by_rank?.adapter = adapterShowChampByRank
    }

    private fun getChamp() {
        mainViewModel.getChampsLiveData()
            .observe(viewLifecycleOwner, Observer {
                adapterShowChampByRank?.addData(it.sortedByDescending { champ: Champ -> champ.rankChamp })
            })
        mainViewModel.getChamps()
    }

    override fun onClickListener(champ: Champ) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_details_champ)
        val nameChamp = dialog.findViewById(R.id.name_champ_dialog) as TextView
        nameChamp.text = champ.name
        val skillName = dialog.findViewById(R.id.skill_name_dialog) as TextView
        skillName.text = champ.skillName
        val activated = dialog.findViewById<TextView>(R.id.activated_dialog)
        activated.text = champ.activated
        val cost = dialog.findViewById(R.id.champ_cost_dialog) as TextView
        cost.text=champ.cost
        val imgCover = dialog.findViewById(R.id.champ_cover_dialog) as ImageView
        Glide.with(imgCover.context)
            .load(champ.linkChampCover)
            .into(imgCover)
        dialog.show()
        val imgAvatar = dialog.findViewById(R.id.skill_avatar_dialog) as ImageView
        Glide.with(imgCover.context)
            .load(champ.linkSkilAvatar)
            .into(imgAvatar)
        dialog.show()
    }
}