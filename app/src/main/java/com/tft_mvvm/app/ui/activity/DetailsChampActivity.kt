package com.tft_mvvm.app.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tft_mvvm.champ.R
import com.tft_mvvm.app.features.champ.model.Champ
//import com.example.presentation.features.champs.model.model.Skill
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.ui.adapter.MyAdapter
import com.tft_mvvm.app.ui.OnItemClickListener
import com.tft_mvvm.champ.databinding.ActivityDetailsChampBinding
import kotlinx.android.synthetic.main.activity_details_champ.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsChampActivity : AppCompatActivity() {
    private var biding: ActivityDetailsChampBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = DataBindingUtil.setContentView(this, R.layout.activity_details_champ)
        getChamp(intent)?.let { champ ->
            setupUI(champ)
        }
    }

    private fun setupUI(champ: Champ) {
        biding?.toolbarTitle?.text = champ.name
        setSupportActionBar(biding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
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
        rv_origin.layoutManager = GridLayoutManager(this, 4)
        rv_classs.layoutManager = GridLayoutManager(this, 4)

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
}


