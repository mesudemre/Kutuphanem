package com.mesutemre.kutuphanem.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 * @Author: mesutemre.celenk
 * @Date: 16.08.2021
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var viewbinding:ViewBinding? = null;
    abstract val bindingInflater:(LayoutInflater,ViewGroup?,Boolean)->VB;
    abstract val layoutName:String;

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = viewbinding as VB;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        onCreateFragment(savedInstanceState);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = bindingInflater.invoke(inflater,container,false);
        onCreateViewFragment((viewbinding as VB).root);
        return requireNotNull(viewbinding).root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        setFragmentInfo();
        onStartFragment();
    }

    abstract fun onCreateFragment(savedInstanceState: Bundle?);
    abstract fun onStartFragment();
    open fun destroyOthers(){};
    open fun onCreateViewFragment(view:View){};
    open fun getFragmentView():View{
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView();
        viewbinding = null;
        destroyOthers();
    }

    private fun setFragmentInfo():Unit{
        val fragment = findNavController()?.currentDestination;
        Log.d("Fragment", fragment?.label?.toString().toString());
        Log.d("Fragment Layout",layoutName);
    }

    open fun onBackPressed() {
        requireActivity().onBackPressed();
    }
}