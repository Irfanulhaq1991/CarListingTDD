package com.irfan.auto1.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.R
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.databinding.FragmentSumaryBinding
import com.irfan.auto1.year.ui.CarYearFragmentDirections


class SummaryFragment : Fragment() {

    private val binding:FragmentSumaryBinding by lazy {
        FragmentSumaryBinding.inflate(requireActivity().layoutInflater)
    }
    private val args:SummaryFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.close.setOnClickListener {
            val action = SummaryFragmentDirections.actionSummaryFragmentToManufacturersFragment()
            findNavController().navigate(action)
        }


        binding.carInfo = args.carInfo
        binding.executePendingBindings()
    }
}