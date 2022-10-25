package com.irfan.auto1.manufacturers.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.ItemLayoutManger
import com.irfan.auto1.common.RcAdaptor
import com.irfan.auto1.databinding.FragmentManufacturersBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManufacturersFragment : Fragment(), ItemLayoutManger<Manufacturer>,
    Observer<ManufacturerUiState> {


    private val viewModel: ManufacturersViewModel by viewModel()

    private val binding: FragmentManufacturersBinding by lazy {
        FragmentManufacturersBinding.inflate(requireActivity().layoutInflater)
    }

    private val adaptor: RcAdaptor<Manufacturer> by lazy {
        RcAdaptor(this).apply {
            bindRecyclerView(binding.recyclerView, rcScrollListener)
        }
    }


    private val rcScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            with(recyclerView.layoutManager as LinearLayoutManager) {
                findLastVisibleItemPosition()
                if (dy > 0 && findLastVisibleItemPosition() == adaptor.itemCount - 1)
                    viewModel.fetchManufacturers()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
            .uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

        binding.retry.setOnClickListener{
            viewModel.fetchManufacturers()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy(adaptor.getItems())
    }

    override fun onRcAdapterReady() {
        viewModel.fetchManufacturers()
    }

    override fun getLayoutId(position: Int): Int {
        return R.layout.row_layout
    }

    override fun bindView(view: View, position: Int, item: Manufacturer) {
        val binding = DataBindingUtil.bind<RowLayoutBinding>(view)!!
        binding.txtTitle.text = item.name
        binding.root.tag = item
        binding.root.setOnClickListener(::navigateToModel)
    }


    private fun navigateToModel(view: View) {
        val manufacturer = view.tag as Manufacturer
        val action =
            ManufacturersFragmentDirections.actionManufacturersFragmentToModelFragment(manufacturer)
        findNavController().navigate(action)
    }

    override fun onChanged(state: ManufacturerUiState) {

        adaptor.update(state.manufacturers)
        handleMessageAndProgressBar(state)
        stateRendered(state)
    }

    private fun handleMessageAndProgressBar(state: ManufacturerUiState) {
        state.errorMessage?.let {
            Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        if (state.manufacturers.isNotEmpty()) {
            binding.bottomProgressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
            binding.mainProgressBar.visibility =  View.GONE
        } else {
            binding.mainProgressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
            binding.retry.visibility = if (state.isError) View.VISIBLE else View.GONE
        }


    }

    private fun stateRendered(state: ManufacturerUiState) {
        if (state.errorMessage != null)
            viewModel.stateRendered()
    }
}