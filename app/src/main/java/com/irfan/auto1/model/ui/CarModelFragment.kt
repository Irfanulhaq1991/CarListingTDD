package com.irfan.auto1.model.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.common.*
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.model.domain.model.CarModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarModelFragment : BaseFragment<CarModel>() {


    private val viewModelCar: CarModelsViewModel by viewModel()


    private val args: CarModelFragmentArgs by navArgs()


    override fun init() {
        super.init()

        viewModelCar.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)
    }

    override fun navigate(view: View) {
        val model = view.tag as CarModel
        val carInfo = args.carInfo.copy(carModel = model)
        val action =
            CarModelFragmentDirections
                .actionModelFragmentToCarYearFragment(carInfo)
        findNavController().navigate(action)
    }

    override fun doFetching() {
        viewModelCar.doFetching(args.carInfo)
    }


    override fun statRendered() {
        viewModelCar.renderingFinished()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentModelBinding.inflate(requireActivity().layoutInflater)

    }

    override fun getTitle(): String {
        return "Manufacturer : ${args.carInfo.carManufacturer.name}"
    }
}