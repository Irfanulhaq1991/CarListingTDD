package com.irfan.auto1.model.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.common.*
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.model.domain.model.CarModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ModelFragment : BaseFragment<CarModel>() {


    private val viewModel: ModelsViewModel by viewModel()


    private val args: ModelFragmentArgs by navArgs()


    override fun init() {
        super.init()

        viewModel.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)
    }

    override fun navigate(view: View) {
        val model = view.tag as CarModel
        val carInfo = args.carInfo.copy(carModel = model)
        val action =
            ModelFragmentDirections
                .actionModelFragmentToCarYearFragment(carInfo)
        findNavController().navigate(action)
    }

    override fun doFetching() {
        viewModel.doFetching(args.carInfo)
    }


    override fun statRendered() {
        viewModel.renderingFinished()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentModelBinding.inflate(requireActivity().layoutInflater)

    }

    override fun getTitle(): String {
        return "Manufacturer : ${args.carInfo.carManufacturer.name}"
    }
}