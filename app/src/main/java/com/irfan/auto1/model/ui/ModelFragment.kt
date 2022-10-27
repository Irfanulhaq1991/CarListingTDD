package com.irfan.auto1.model.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.irfan.auto1.R
import com.irfan.auto1.common.ItemLayoutManger
import com.irfan.auto1.common.RcAdaptor
import com.irfan.auto1.databinding.FragmentModelBinding
import com.irfan.auto1.databinding.RowLayoutBinding
import com.irfan.auto1.model.domain.model.Model
import org.koin.androidx.viewmodel.ext.android.viewModel


class ModelFragment: Fragment(), ItemLayoutManger<Model>, Observer<ModelUiState> {

    private val binding: FragmentModelBinding by lazy {
        FragmentModelBinding.inflate(requireActivity().layoutInflater)
    }

    private val viewModel: ModelsViewModel by viewModel()

    private val adaptor: RcAdaptor<Model> by lazy {
        RcAdaptor(this).apply {
            bindRecyclerView(binding.recyclerView)
        }
    }

    private val args: ModelFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            viewModel.fetchModels(args.manufacturer.id)
        }

        binding
            .searchView
            .doOnTextChanged { query, _, _, _ ->
                viewModel
                    .search(query.toString())
            }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.manufacturerName.text =
            "${binding.manufacturerName.text} : ${args.manufacturer.name}"
    }


    override fun onRcAdapterReady() {
        viewModel.fetchModels(args.manufacturer.id)
    }

    override fun getLayoutId(position: Int): Int {
        return R.layout.row_layout
    }

    override fun bindView(view: View, position: Int, item: Model) {
        val binding = DataBindingUtil.bind<RowLayoutBinding>(view)!!
        binding.txtTitle.text = item.name
        binding.root.tag = item
        binding.root.setOnClickListener(::navigateToYear)
    }

    private fun navigateToYear(view: View) {
        TODO("Not yet implemented")
    }

    override fun onChanged(state: ModelUiState) {
        adaptor.setData(state.models,state.update)
        handleMessageAndProgressBar(state)
        stateRendered(state)
    }

    private fun handleMessageAndProgressBar(state: ModelUiState) {
        state.errorMessage?.let {
            Snackbar.make(requireContext(), binding.root.rootView, it, Snackbar.LENGTH_SHORT).show()
        }
            binding.searchView.visibility = if (state.isError || state.loading) View.GONE else View.VISIBLE
            binding.mainProgressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
            binding.retry.visibility = if (state.isError) View.VISIBLE else View.GONE
    }

    private fun stateRendered(state: ModelUiState) {
        if (state.errorMessage != null)
            viewModel.stateRendered()
    }
}