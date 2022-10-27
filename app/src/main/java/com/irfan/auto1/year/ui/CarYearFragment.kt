package com.irfan.auto1.year.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.ItemLayoutManger
import com.irfan.auto1.common.RcAdaptor
import com.irfan.auto1.databinding.FragmentCarYearBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.year.domain.model.CarYear
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarYearFragment : Fragment(),ItemLayoutManger<CarYear>,Observer<CarYearsUiState> {
    private val binding: FragmentCarYearBinding by lazy {
        FragmentCarYearBinding.inflate(requireActivity().layoutInflater)
    }
    private val viewModel: CarYearsViewModel by viewModel()

    private val adaptor: RcAdaptor<CarYear> by lazy {
        RcAdaptor(this).apply {
            bindRecyclerView(binding.recyclerView)
        }
    }

    private val args: CarYearFragmentArgs by navArgs()

    private val title:String by lazy {
        "Manufacturer: ${args.carInfo.manufacturer.name}  Model: ${args.carInfo.model.name}"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel
            .uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

        binding.retry.setOnClickListener {
            viewModel.fetchCarYears(args.carInfo)
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.manufacturerName.text = title

    }

    override fun onRcAdapterReady() {
       viewModel.fetchCarYears(args.carInfo)
    }

    override fun getLayoutId(position: Int): Int {
        return R.layout.row_layout
    }

    override fun bindView(view: View, position: Int, item: CarYear) {
        val binding = DataBindingUtil.bind<RowLayoutBinding>(view)!!
        binding.txtTitle.text = item.name
        binding.root.tag = item
        binding.root.setOnClickListener(::navigateToSummery)
    }

    private fun navigateToSummery(view: View) {
        val carYear = view.tag as CarYear
        val carInfo = args.carInfo.copy(year = carYear)
        val action = CarYearFragmentDirections.actionCarYearFragmentToSummaryFragment(carInfo)
        findNavController().navigate(action)
    }


    override fun onChanged(state: CarYearsUiState) {
        adaptor.setData(state.carYears,state.update)
        handleMessageAndProgressBar(state)
        stateRendered(state)
    }
    private fun handleMessageAndProgressBar(state: CarYearsUiState) {
        state.errorMessage?.let {
            Snackbar.make(requireContext(), binding.root.rootView, it, Snackbar.LENGTH_SHORT).show()
        }
        binding.mainProgressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
        binding.retry.visibility = if (state.isError) View.VISIBLE else View.GONE
    }

    private fun stateRendered(state: CarYearsUiState) {
        if (state.errorMessage != null)
            viewModel.stateRendered()
    }
}