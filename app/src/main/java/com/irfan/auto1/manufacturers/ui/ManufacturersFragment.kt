package com.irfan.auto1.manufacturers.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.citysearch.view.ItemLayoutManger
import com.example.citysearch.view.RcAdaptor
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.databinding.FragmentManufacturersBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManufacturersFragment : Fragment(), ItemLayoutManger<Manufacturer>, Observer<ManufacturerUiState> {


    private val viewModel: ManufacturersViewModel by viewModel()

    private val binding: FragmentManufacturersBinding  by lazy {
        FragmentManufacturersBinding.inflate(requireActivity().layoutInflater)
    }

    private val adaptor: RcAdaptor<Manufacturer> by lazy {
        RcAdaptor(this).apply { bindRecyclerView(binding.recyclerView) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manufacturers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
            .uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)
    }

    override fun onRcAdapterReady() {
        viewModel.fetchManufacturers()
    }

    override fun getLayoutId(position: Int): Int {
       return  R.layout.row_layout
    }

    override fun bindView(view: View, position: Int, item: Manufacturer) {
        val binding = DataBindingUtil.bind<RowLayoutBinding>(view)!!
        binding.txtTitle.text = item.name
        binding.root.tag = item
        binding.root.setOnClickListener(::navigateToMap)
    }
    private fun navigateToMap(view: View) {
        val manufacturer = view.tag as Manufacturer
        val action = ManufacturersFragmentDirections.actionManufacturersFragmentToModelFragment(manufacturer)
        findNavController().navigate(action)
    }

    override fun onChanged(state: ManufacturerUiState) {
        adaptor.update(state.manufacturers)
        showErrorMessage(state.errorMessage)
        stateRendered(state)
    }
    private fun showErrorMessage(message: String?) {
        message?.let {
            Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun stateRendered(state: ManufacturerUiState) {
        if (state.errorMessage != null)
            viewModel.stateRendered()
    }
}