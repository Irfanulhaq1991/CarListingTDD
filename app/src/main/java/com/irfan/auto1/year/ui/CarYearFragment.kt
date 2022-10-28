package com.irfan.auto1.year.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.common.BaseFragment
import com.irfan.auto1.databinding.FragmentCarYearBinding
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
        return "Manufacturer: ${args.carInfo.carManufacturer.name}  Model: ${args.carInfo.carModel.name}"
    }
}