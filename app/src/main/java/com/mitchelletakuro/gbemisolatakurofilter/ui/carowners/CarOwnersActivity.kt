package com.mitchelletakuro.gbemisolatakurofilter.ui.carowners

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.mitchelletakuro.gbemisolatakurofilter.R
import com.mitchelletakuro.gbemisolatakurofilter.databinding.ActivityCarOwnersBinding
import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import com.mitchelletakuro.gbemisolatakurofilter.ui.carowners.viewmodel.CarOwnersViewModel
import com.mitchelletakuro.gbemisolatakurofilter.utils.FILTER_INTENT_KEY
import java.util.*

 class CarOwnersActivity : AppCompatActivity() {
    private val viewModel: CarOwnersViewModel by lazy {
        ViewModelProvider(this)[CarOwnersViewModel::class.java]
    }
    private val binding: ActivityCarOwnersBinding by lazy {
        ActivityCarOwnersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val filterItem = intent.extras?.getParcelable<FilterItem>(FILTER_INTENT_KEY) as FilterItem
        binding.carOwnerRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }
}
