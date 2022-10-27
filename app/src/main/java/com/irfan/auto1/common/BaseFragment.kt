package com.irfan.auto1.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.BR
import com.irfan.auto1.R
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.model.ui.ModelsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


abstract class BaseFragment<T>() :
    Fragment(),
    ItemLayoutManger<T>,
    Observer<BaseUIState<T>> {

    protected abstract fun navigate(view: View)
    protected abstract fun doFetching()
    protected abstract fun statRendered()
    protected abstract fun getFragmentBinding(): ViewDataBinding
    protected abstract fun getTitle(): String



    protected val adaptor: RcAdaptor<T> by lazy {
        RcAdaptor(this).apply {
            bindRecyclerView(binding.root.findViewById(R.id.recyclerView))
        }
    }
    protected val binding: ViewDataBinding by lazy {
        getFragmentBinding()
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

    protected open fun init() {

        binding.root.findViewById<View>(R.id.retry).setOnClickListener {
            doFetching()
        }

        binding.root.findViewById<View>(R.id.btn_back)?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.root.findViewById<TextView>(R.id.title).apply { text = getTitle() }

    }

    override fun onRcAdapterReady() {
        doFetching()
    }

    override fun getLayoutId(position: Int): Int {
        return R.layout.row_layout
    }

    override fun bindView(view: View, position: Int, item: T) {
        val binding = DataBindingUtil.bind<RowLayoutBinding>(view)!!
        binding.setVariable(BR.data, item)
        binding.executePendingBindings()
        binding.root.tag = item
        binding.root.setOnClickListener(::navigate)
    }


    protected open fun handleProgressBar(state: BaseUIState<T>) {

        binding.root.findViewById<View>(R.id.search_view)?.visibility =
            if (state.isError || state.loading) View.GONE else View.VISIBLE

        binding.root.findViewById<View>(R.id.mainProgressBar).visibility =
            if (state.loading) View.VISIBLE else View.GONE

        binding.root.findViewById<View>(R.id.retry).visibility =
            if (state.isError) View.VISIBLE else View.GONE
    }

    private fun stateRendered(state: BaseUIState<T>) {
        if (state.errorMessage != null || state.update)
            statRendered()
    }

   private fun handleErrorMessage(state: BaseUIState<T>){
       state.errorMessage?.let {
           Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_SHORT).show()
       }
    }
    override fun onChanged(state: BaseUIState<T>) {
        adaptor.setData(state.data)
        handleProgressBar(state)
        handleErrorMessage(state)
        stateRendered(state)

    }
}