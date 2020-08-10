package com.tft_mvvm.app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.champ.R

class Dialog(context: Context, champ: Champ) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_show_details_champ)
        val nameChamp = findViewById(R.id.name_champ_dialog) as TextView
        nameChamp.text = champ.name
        val skillName = findViewById(R.id.skill_name_dialog) as TextView
        skillName.text = champ.skillName
        val activated = findViewById<TextView>(R.id.activated_dialog)
        activated.text = champ.activated
        val cost = findViewById(R.id.champ_cost_dialog) as TextView
        cost.text = champ.cost
        val imgCover = findViewById(R.id.champ_cover_dialog) as ImageView
        Glide.with(imgCover.context)
            .load(champ.linkChampCover)
            .into(imgCover)
        val imgAvatar = findViewById(R.id.skill_avatar_dialog) as ImageView
        Glide.with(imgCover.context)
            .load(champ.linkSkilAvatar)
            .into(imgAvatar)
    }
}