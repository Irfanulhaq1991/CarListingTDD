package com.irfan.auto1.model.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.*
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.model.domain.model.Model
import org.koin.androidx.viewmodel.ext.android.viewModel


class ModelFragment : BaseFragment<Model>() {


    private val viewModel: ModelsViewModel by viewModel()


    private val args: ModelFragmentArgs by navArgs()


    override fun init() {
        super.init()

        viewModel.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)
    }

    override fun navigate(view: View) {
        val model = view.tag as Model
        val carInfo = args.carInfo.copy(model = model)
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
        return "Manufacturer : ${args.carInfo.manufacturer.name}"
    }
}