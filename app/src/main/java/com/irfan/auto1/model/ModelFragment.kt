package com.irfan.auto1.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.R
import com.irfan.auto1.databinding.FragmentManufacturersBinding
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.manufacturers.domain.model.Manufacturer


class ModelFragment : Fragment() {
    private val args: ModelFragmentArgs by navArgs()

    private val manufacturer: Manufacturer by lazy {
        args.manufacturer
    }

    val binding:FragmentModelBinding by lazy {
        FragmentModelBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_model, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = manufacturer.name
    }
}