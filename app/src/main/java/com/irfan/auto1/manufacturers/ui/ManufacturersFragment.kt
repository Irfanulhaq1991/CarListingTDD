package com.irfan.auto1.manufacturers.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.BaseFragment
import com.irfan.auto1.common.BaseUIState
import com.irfan.auto1.common.ItemLayoutManger
import com.irfan.auto1.common.RcAdaptor
import com.irfan.auto1.databinding.FragmentManufacturersBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManufacturersFragment : BaseFragment<Manufacturer>() {


    private val viewModel: ManufacturersViewModel by viewModel()


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


    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy(adaptor.getItems())
    }



    override fun init() {
        super.init()
        viewModel.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

        binding.root.findViewById<RecyclerView>(R.id.recyclerView)
            .apply { addOnScrollListener(rcScrollListener) }
    }


    override fun handleProgressBar(state: BaseUIState<Manufacturer>) {

        if (state.data.isNotEmpty()) {
            binding.root.findViewById<View>(R.id.bottomProgressBar).visibility =
                if (state.loading) View.VISIBLE else View.GONE
            binding.root.findViewById<View>(R.id.mainProgressBar).visibility = View.GONE
        } else {
            binding.root.findViewById<View>(R.id.mainProgressBar).visibility =
                if (state.loading) View.VISIBLE else View.GONE
            binding.root.findViewById<View>(R.id.retry).visibility =
                if (state.isError) View.VISIBLE else View.GONE
        }


    }


    override fun navigate(view: View) {
        val manufacturer = view.tag as Manufacturer
        val action =
            ManufacturersFragmentDirections.actionManufacturersFragmentToModelFragment(manufacturer)
        findNavController().navigate(action)
    }

    override fun doFetching() {
        viewModel.fetchManufacturers()
    }

    override fun statRendered() {
        viewModel.stateRendered()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentManufacturersBinding.inflate(requireActivity().layoutInflater)
    }

    override fun getTitle(): String {
        return "Manufacturers"
    }
}