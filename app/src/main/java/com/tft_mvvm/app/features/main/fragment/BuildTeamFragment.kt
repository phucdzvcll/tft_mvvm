package com.tft_mvvm.app.features.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.main.OnClickListenerPickChamp
import com.tft_mvvm.app.features.main.adapter.AdapterShowChampByOriginAndClass
import com.tft_mvvm.app.features.main.model.ClassAndOriginContent
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.fragment_build_team.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuildTeamFragment : Fragment(),
    OnClickListenerPickChamp {
    private val showChampByRankViewModel: ShowChampByRankViewModel by viewModel()
    private var adapterShowChampByOriginAndClass: AdapterShowChampByOriginAndClass? = null
    private val listImageView = mutableListOf<ImageView?>()
    val listChamp = mutableListOf<ClassAndOriginContent.Champ>()
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
        setupPickChamp()
        deleteAll()
        showChampByRankViewModel.getAllClassAndOriginName()
    }

    private fun setupPickChamp() {
        val listImage = listOf<ImageView?>(
            champ1,
            champ2,
            champ3,
            champ4,
            champ5,
            champ6,
            champ7,
            champ8,
            champ9,
            champ10
        )
        listImage.forEach { img ->
            img?.setOnClickListener {
                listChamp.forEach { champ ->

                }
                img.tag = "blank"
                img.setImageResource(R.drawable.back)
                img.setBackgroundResource(R.drawable.back)

            }
            listImageView.add(img)
        }
    }

    private fun deleteAll() {
        btn_delete?.setOnClickListener {
            listChamp.clear()
            btn_delete?.text = "XÓA (0/10)"
            listImageView.forEach { img->
                img?.tag = "blank"
                img?.setImageResource(R.drawable.back)
                img?.setBackgroundResource(R.drawable.back)
            }
        }
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

    override fun onClick(champ: ClassAndOriginContent.Champ) {
        if (listChamp.size < 10) {
            listChamp.add(champ)
            val size = listChamp.size
            btn_delete?.text = "XÓA ($size/10)"
        }
        listImageView.forEach {
            if (it?.tag == "blank") {
                Glide.with(it.context)
                    .load(champ.imgUrl)
                    .into(it)
                it.tag = champ.id.toInt()
                when (champ.cost) {
                    "1" -> it.setBackgroundResource(R.drawable.background_1_gold)
                    "2" -> it.setBackgroundResource(R.drawable.background_2_gold)
                    "3" -> it.setBackgroundResource(R.drawable.background_3_gold)
                    "4" -> it.setBackgroundResource(R.drawable.background_4_gold)
                    "5" -> it.setBackgroundResource(R.drawable.background_5_gold)
                }
                return
            }
        }
    }
}
