package com.irfan.auto1.model.ui

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.irfan.auto1.R
import com.irfan.auto1.common.*
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.model.domain.model.CarModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarModelFragment : BaseFragment<CarModel>() {


    private val carViewModel: CarModelsViewModel by viewModel()


    private val args: CarModelFragmentArgs by navArgs()


    override fun init() {
        super.init()

        carViewModel.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

        binding.root.findViewById<AppCompatEditText>(R.id.search_view)
            .doOnTextChanged { query, _, _, _ ->
                carViewModel.search(query.toString())
            }
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
        carViewModel.doFetching(args.carInfo)
    }


    override fun statRendered() {
        carViewModel.renderingFinished()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentModelBinding.inflate(requireActivity().layoutInflater)

    }

    override fun getTitle(): String {
        return "Manufacturer : ${args.carInfo.carManufacturer.name}"
    }
}