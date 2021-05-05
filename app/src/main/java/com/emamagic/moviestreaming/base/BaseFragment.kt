package com.emamagic.moviestreaming.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.util.helper.conectivity.ConnectionLiveData
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<VB: ViewBinding ,STATE: BaseState ,EFFECT: BaseEffect ,EVENT: BaseEvent ,VM: BaseViewModel<STATE ,EFFECT ,EVENT>>: Fragment() {

    private  var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var loading: FrameLayout
    private var callback: OnBackPressedCallback? = null
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(inflater ,container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = binding.root.rootView?.findViewById(R.id.my_loading)!!

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { renderViewState(it) }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEffect.collect { renderViewEffect(it) }
        }

        onFragmentBackPressed(viewLifecycleOwner){ findNavController().popBackStack() }
    }

    protected fun showLoading(isDim: Boolean = false){
        if (loading.visibility != View.VISIBLE){
            if (isDim) loading.setBackgroundColor(Color.parseColor("#cc000000"))
            loading.visibility = View.VISIBLE
        }
    }

    protected fun hideLoading(){
        if (loading.visibility != View.GONE){
            loading.visibility = View.GONE
        }
    }

    protected inline fun subscribeOnNetworkStatusChange(crossinline call: (Boolean) -> Unit){
        connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(viewLifecycleOwner){ isNetworkAvailable ->
            call(isNetworkAvailable)
        }
    }

    abstract val viewModel: VM

    abstract fun getFragmentBinding(inflater: LayoutInflater ,container: ViewGroup?): VB

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)

    fun onFragmentBackPressed(owner: LifecycleOwner, call: () -> Unit) {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                call()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner, callback!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (callback != null){
            callback?.isEnabled = false
            callback?.remove()
        }
    }

}

