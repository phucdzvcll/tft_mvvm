package com.tft_mvvm.app.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.section.MySection
import com.tft_mvvm.champ.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_show_champ_by_rank.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowChampByRankFragment : Fragment(),OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private var sectionedAdapter: SectionedRecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_champ_by_rank, container, false)
        createView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getChamp()
        setupUI()

    }

    private fun createView(view: View) {
        sectionedAdapter = SectionedRecyclerViewAdapter()
        recyclerView = view.findViewById(R.id.rv_by_name)
    }

    private fun setupUI() {
        val glm = GridLayoutManager(requireContext(), 6)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (sectionedAdapter?.getSectionItemViewType(position) == SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER) {
                    6
                } else 1
            }
        }
        recyclerView?.layoutManager = glm
        recyclerView?.adapter = sectionedAdapter
    }

    private fun getChamp() {
        mainViewModel.loadChampByRank()
            .observe(viewLifecycleOwner, Observer {
                for ((key, value) in it) {
                    if (value.isNotEmpty()) {
                        sectionedAdapter?.addSection(MySection(key, value,this))
                        sectionedAdapter?.notifyDataSetChanged()
                    }
                }
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