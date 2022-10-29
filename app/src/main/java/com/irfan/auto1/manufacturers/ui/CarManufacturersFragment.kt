package com.irfan.auto1.manufacturers.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.auto1.R
import com.irfan.auto1.common.*
import com.irfan.auto1.databinding.FragmentManufacturersBinding
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarManufacturersFragment : BaseFragment<CarManufacturer>() {


    private val viewModelCar: CarManufacturersViewModel by viewModel()


    private val rcScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            with(recyclerView.layoutManager as LinearLayoutManager) {
                findLastVisibleItemPosition()
                if (dy > 0 && findLastVisibleItemPosition() == adaptor.itemCount - 1)
                    viewModelCar.fetchManufacturers()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModelCar.onDestroy()
    }


    override fun init() {
        super.init()
        viewModelCar.uiStateUpdater
            .apply { adaptor }
            .observe(viewLifecycleOwner, this)

        binding.root.findViewById<RecyclerView>(R.id.recyclerView)
            .apply { addOnScrollListener(rcScrollListener) }
    }


    override fun handleProgressBar(state: BaseUIState<CarManufacturer>) {

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
        val manufacturer = view.tag as CarManufacturer
        val action =
            CarManufacturersFragmentDirections.actionManufacturersFragmentToModelFragment(
                CarInfo(
                    carManufacturer = manufacturer
                )
            )
        findNavController().navigate(action)
    }

    override fun doFetching() {
        viewModelCar.fetchManufacturers()
    }

    override fun statRendered() {
        viewModelCar.renderingFinished()
    }

    override fun getFragmentBinding(): ViewDataBinding {
        return FragmentManufacturersBinding.inflate(requireActivity().layoutInflater)
    }

    override fun getTitle(): String {
        return "Manufacturers"
    }
}