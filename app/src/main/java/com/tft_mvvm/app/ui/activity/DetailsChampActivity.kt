package com.tft_mvvm.app.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.app.features.champ.model.Champ
import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.app.ui.adapter.AdapterShowByOriginAndClass
import com.tft_mvvm.champ.R
import com.tft_mvvm.champ.databinding.ActivityDetailsChampBinding
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsChampActivity : AppCompatActivity(), OnItemClickListener {
    private var biding: ActivityDetailsChampBinding? = null
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var adapterShowByOrigin: AdapterShowByOriginAndClass? = null
    private var adapterShowByClass: AdapterShowByOriginAndClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = DataBindingUtil.setContentView(this, R.layout.activity_details_champ)
        getChamp(intent)?.let { champ ->
            setupUI(champ)
            getChampByOrigin(champ)
            getChampByClass(champ)
        }
    }

    private fun setupUI(champ: Champ) {
        biding?.toolbarTitle?.text = champ.name
        setSupportActionBar(biding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var exit = false
        toolbar.setNavigationOnClickListener {
            if (exit) {
                finish()
            } else {
                Toast.makeText(
                    this, "Nhấn nút back lần nữa để quay lại",
                    Toast.LENGTH_SHORT
                ).show()
                exit = true
                object : CountDownTimer(3000, 1000) {
                    override fun onTick(l: Long) {}
                    override fun onFinish() {
                        exit = false
                    }
                }.start()
            }
        }
        biding?.origin?.text = champ.origin
        biding?.classs?.text = champ.classs
        biding?.skillAvatar?.let {
            Glide.with(this)
                .load(champ.linkSkilAvatar)
                .into(it)
        }
        biding?.champCover?.let {
            Glide.with(this)
                .load(champ.linkChampCover)
                .into(it)
        }
        skill_name.text = champ.skillName
        activated.text = champ.activated

        biding?.rvOrigin?.layoutManager = GridLayoutManager(this, 6)
        adapterShowByOrigin = AdapterShowByOriginAndClass(arrayListOf(), this)
        biding?.rvOrigin?.adapter = adapterShowByOrigin

        biding?.rvClasss?.layoutManager = GridLayoutManager(this, 6)
        adapterShowByClass = AdapterShowByOriginAndClass(arrayListOf(), this)
        biding?.rvClasss?.adapter = adapterShowByClass
    }

    private fun getChampByOrigin(champ: Champ) {
        detailsViewModel.getChampsByOrigin(champ.origin)
        detailsViewModel.getChampsByOriginLiveData()
            .observe(this, Observer {
                adapterShowByOrigin?.addData(it)
            })

    }

    private fun getChampByClass(champ: Champ) {
        detailsViewModel.getChampsByClass(champ.classs)
        detailsViewModel.getChampsByClassLiveData()
            .observe(this, Observer {
                adapterShowByClass?.addData(it)
            })

    }

    companion object {
        private const val CHAMP_EXTRA = "champ_extra"

        fun newIntent(context: Context, champ: Champ): Intent {
            val intent = Intent(context, DetailsChampActivity::class.java)
            intent.putExtra(CHAMP_EXTRA, champ)
            return intent
        }

        fun getChamp(intent: Intent): Champ? {
            return intent.getSerializableExtra(CHAMP_EXTRA) as? Champ
        }
    }

    override fun onClickListener(champ: Champ) {
        val dialog = Dialog(this)
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


