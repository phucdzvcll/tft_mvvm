package com.tft_mvvm.app.features.dialog_show_details_champ

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.dialog_show_details_champ.model.ChampDialogModel
import com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel.DialogShowDetailsChampViewModel
import com.tft_mvvm.champ.R
import kotlinx.android.synthetic.main.dialog_show_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel

class DialogShowDetailsChampInTeam : DialogFragment() {

    private val dialogShowDetailsChampViewModel: DialogShowDetailsChampViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_show_details_champ, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parent_dialog_champ?.setOnClickListener { dismiss() }
        observeViewModel()
        getChampId()?.let { champId ->
            dialogShowDetailsChampViewModel.getChampById(champId)
        }
        getListItem()?.let { listItem->
            setupItem(listItem)
        }
    }

    private fun setChampModel(champ: ChampDialogModel) {
        name_champ_dialog?.text = champ.name
        skill_name_dialog?.text = champ.skillName
        activated_dialog?.text = champ.activated
        champ_cost_dialog?.text = champ.cost
        Glide.with(champ_cover_dialog.context)
            .load(champ.linkChampCover)
            .into(champ_cover_dialog)
        Glide.with(skill_avatar_dialog.context)
            .load(champ.linkSkillAvatar)
            .into(skill_avatar_dialog)
    }

    private fun setupItem(listItem: List<ChampDialogModel.Item>) {

        if (listItem.isNotEmpty()) {
            suitable_item_dialog_img_1.visibility = View.VISIBLE
            Glide.with(suitable_item_dialog_img_1.context)
                .load(listItem[0].itemAvatar)
                .into(suitable_item_dialog_img_1)
            suitable_item_dialog_img_1.setOnClickListener {
                Toast.makeText(requireContext(), listItem[0].itemName, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (listItem.size >= 2) {
            suitable_item_dialog_img_2.visibility = View.VISIBLE
            Glide.with(suitable_item_dialog_img_2.context)
                .load(listItem[1].itemAvatar)
                .into(suitable_item_dialog_img_2)
            suitable_item_dialog_img_2.setOnClickListener {
                Toast.makeText(requireContext(), listItem[1].itemName, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (listItem.size == 3) {
            suitable_item_dialog_img_3.visibility = View.VISIBLE
            Glide.with(suitable_item_dialog_img_3.context)
                .load(listItem[2].itemAvatar)
                .into(suitable_item_dialog_img_3)
            suitable_item_dialog_img_3.setOnClickListener {
                Toast.makeText(requireContext(), listItem[2].itemName, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun observeViewModel() {
        dialogShowDetailsChampViewModel.getChampByDialogLiveData().observe(viewLifecycleOwner,
            Observer {
                setChampModel(it)
            })
    }

    private fun getChampId(): String? {
        return arguments?.getString(CHAMP_ID_EXTRA)
    }

    private fun getListItem(): List<ChampDialogModel.Item>? {
        return arguments?.getParcelableArrayList(ITEM_EXTRA)
    }

    companion object {
        private const val CHAMP_ID_EXTRA = "champ_id_extra"
        private const val ITEM_EXTRA = "item_extra"

        fun newInstance(id: String, listItem: List<ChampDialogModel.Item>): DialogShowDetailsChampInTeam {
            val dialogShowDetailsChampInTeam =
                DialogShowDetailsChampInTeam()
            dialogShowDetailsChampInTeam.arguments = bundleOf(
                CHAMP_ID_EXTRA to id,
                ITEM_EXTRA to listItem
            )
            return dialogShowDetailsChampInTeam
        }
    }
}