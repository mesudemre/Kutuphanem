package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.YayinEviAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ParametreYayineviFragmentBinding
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.viewmodels.ParametreYayineviViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParametreYayineviFragment: BaseFragment<ParametreYayineviFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ParametreYayineviFragmentBinding
            = ParametreYayineviFragmentBinding::inflate;
    override val layoutName = "parametre_yayinevi_fragment.xml";

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val viewModel:ParametreYayineviViewModel by viewModels();
    private var adapter:YayinEviAdapter? = null;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = YayinEviAdapter(arrayListOf(),viewModel,viewLifecycleOwner,token);
        viewModel.yayinEviListeGetir(false);
        binding.yayinEviListeRw.layoutManager = LinearLayoutManager(context);
        binding.yayinEviListeRw.adapter = adapter;

        observeLiveData();

        binding.yayinEviSwipeRefreshLayout.setOnRefreshListener {
            binding.yayinEviListeRw.visibility = View.GONE;
            binding.yayinEviErrorTextId.visibility = View.GONE;
            binding.yayinEviProgressBar.visibility = View.VISIBLE;

            binding.yayinEviSwipeRefreshLayout.isRefreshing = false;

            viewModel.yayinEviListeGetir(true);
        }

        binding.yayinEviEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("yayinevi");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.yayineviListe.observe(viewLifecycleOwner, Observer { yayineviListe->
            yayineviListe?.let {
                adapter?.updateYayineviListe(yayineviListe);
            }
        });

        viewModel.yayinEviError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if(it){
                    binding.yayinEviErrorTextId.visibility = View.VISIBLE;
                    binding.yayinEviListeRw.visibility = View.GONE;
                }else{
                    binding.yayinEviListeRw.visibility = View.VISIBLE;
                    binding.yayinEviErrorTextId.visibility = View.GONE;
                }
            }
        });

        viewModel.yayinEviLoading.observe(viewLifecycleOwner, Observer {loading->
            loading.let {
                if(it){
                    binding.yayinEviProgressBar.visibility = View.VISIBLE;
                    binding. yayinEviListeRw.visibility = View.GONE;
                    binding.yayinEviErrorTextId.visibility = View.GONE;
                }else{
                    binding.yayinEviProgressBar.visibility = View.GONE;
                }
            }
        })
    }

    override fun destroyOthers() {
        adapter = null;
    }
}