package com.irfan.auto1.year.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.BaseFragment
import com.irfan.auto1.common.ItemLayoutManger
import com.irfan.auto1.common.RcAdaptor
import com.irfan.auto1.databinding.FragmentCarYearBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.year.domain.model.CarYear
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarYearFragment : BaseFragment<CarYear>() {

    private val viewModel: CarYearsViewModel by viewModel()


    private val args: CarYearFragmentArgs by navArgs()


    override fun init() {
        viewModel
            .uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

    }


    override fun navigate(view: View) {
        val carYear = view.tag as CarYear
        val carInfo = args.carInfo.copy(year = carYear)
        val action = CarYearFragmentDirections.actionCarYearFragmentToSummaryFragment(carInfo)
        findNavController().navigate(action)
    }

    override fun doFetching() {
        viewModel.fetchCarYears(args.carInfo)
    }

    override fun statRendered() {
        viewModel.renderingFinished()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentCarYearBinding.inflate(requireActivity().layoutInflater)
    }

    override fun getTitle(): String {
        return "Manufacturer: ${args.carInfo.manufacturer.name}  Model: ${args.carInfo.model.name}"
    }
}