package com.example.chartstest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.chartstest.R
import com.example.chartstest.databinding.FragmentMainContentBinding
import com.example.chartstest.utils.extentions.viewBinding


class MainContentFragment : Fragment(R.layout.fragment_main_content) {

    private val binding by viewBinding(FragmentMainContentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonWeek.setOnClickListener {
            val action =
                MainContentFragmentDirections.actionFirstFragmentToSecondFragment(R.string.weekly_path)
            findNavController(this).navigate(action)
            return@setOnClickListener
        }

        binding.buttonMonth.setOnClickListener {
            val action =
                MainContentFragmentDirections.actionFirstFragmentToSecondFragment(R.string.monthly_path)
            findNavController(this).navigate(action)
            return@setOnClickListener
        }
    }
}