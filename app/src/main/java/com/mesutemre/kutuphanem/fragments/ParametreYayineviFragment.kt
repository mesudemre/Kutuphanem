package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.YayinEviAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ParametreYayineviFragmentBinding
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.ParametreYayineviViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParametreYayineviFragment: BaseFragment<ParametreYayineviFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ParametreYayineviFragmentBinding
            = ParametreYayineviFragmentBinding::inflate;
    override val layoutName = "parametre_yayinevi_fragment.xml";

    private val viewModel:ParametreYayineviViewModel by viewModels();
    private var adapter:YayinEviAdapter? = null;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        adapter = YayinEviAdapter(arrayListOf(),::deleteYayinevi);
        viewModel.yayinEviListeGetir(false);
        binding.yayinEviListeRw.layoutManager = LinearLayoutManager(context);
        binding.yayinEviListeRw.adapter = adapter;

        observeLiveData();

        binding.yayinEviSwipeRefreshLayout.setOnRefreshListener {
            binding.yayinEviListeRw.hideComponent();
            binding.yayinEviErrorTextId.hideComponent();
            binding.yayinEviProgressBar.showComponent();

            binding.yayinEviSwipeRefreshLayout.isRefreshing = false;

            viewModel.yayinEviListeGetir(true);
        }

        binding.yayinEviEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("yayinevi");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.yayinEviListeResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.yayinEviProgressBar.showComponent();
                    binding. yayinEviListeRw.hideComponent();
                    binding.yayinEviErrorTextId.hideComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.yayinEviErrorTextId.showComponent();
                    binding.yayinEviListeRw.hideComponent();
                    binding.yayinEviProgressBar.hideComponent();
                }
                is BaseResourceEvent.Success->{
                    binding.yayinEviProgressBar.hideComponent();
                    binding.yayinEviErrorTextId.hideComponent();
                    binding. yayinEviListeRw.showComponent();
                    adapter?.updateYayineviListe(it.data!!);
                }
            }
        });
    }

    private fun deleteYayinevi(jsonStr:String) {
        viewModel.deleteYayineviParametre(jsonStr);
        observeYayineviSilLiveData();
    }

    private fun observeYayineviSilLiveData(){
        viewModel.yayinEviSilResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.yayinEviProgressBar.showComponent();
                    binding.yayinEviErrorTextId.hideComponent();
                    binding.yayinEviListeRw.hideComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.yayinEviProgressBar.hideComponent();
                    showSnackBar(getFragmentView(),it.message!!, ERROR);
                }
                is BaseResourceEvent.Success->{
                    binding.yayinEviProgressBar.hideComponent();
                    binding.yayinEviErrorTextId.hideComponent();
                    showSnackBar(getFragmentView(),it.data!!.statusMessage, SUCCESS);
                    viewModel.yayinEviListeGetir(false);
                    observeLiveData();
                }
            }
        });
    }

    override fun destroyOthers() {
        adapter = null;
    }
}