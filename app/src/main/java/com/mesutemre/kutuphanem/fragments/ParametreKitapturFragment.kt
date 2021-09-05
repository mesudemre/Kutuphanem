package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.KitapTurAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ParametreKitapturFragmentBinding
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.ParametreKitapturViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.parametre_kitaptur_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class ParametreKitapturFragment:BaseFragment<ParametreKitapturFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ParametreKitapturFragmentBinding
            = ParametreKitapturFragmentBinding::inflate;
    override val layoutName = "parametre_kitaptur_fragment.xml";

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    private val viewModel: ParametreKitapturViewModel by viewModels();
    private var adapter:KitapTurAdapter? = null;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = KitapTurAdapter(arrayListOf(),viewModel,viewLifecycleOwner,token);
        viewModel.kitapTurListeGetir(false);
        binding.kitapTurListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapTurListeRw.adapter = adapter;

        observeLiveData();

        binding.kitapTurSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapTurProgressBar.showComponent();
            binding.kitapTurErrorTextId.hideComponent();
            binding.kitapTurListeRw.hideComponent();

            binding.kitapTurSwipeRefreshLayout.isRefreshing = false;
            viewModel.kitapTurListeGetir(true);
        }

        kitapTurEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("kitaptur");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.kitapturListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe?.let {
                binding.kitapTurListeRw.showComponent();
                adapter?.updateKitapTurListe(kitapTurListe);
            }
        });

        viewModel.kitapTurError.observe(viewLifecycleOwner,Observer{error->
            error.let {
                if(it){
                    binding.kitapTurProgressBar.hideComponent();
                    binding.kitapTurErrorTextId.showComponent();
                    binding.kitapTurListeRw.hideComponent();
                }else{
                    binding.kitapTurProgressBar.hideComponent();
                }
            }
        });

        viewModel.kitapTurLoading.observe(viewLifecycleOwner, Observer { loading->
            loading.let {
                if(it){
                    binding.kitapTurProgressBar.showComponent();
                    binding.kitapTurErrorTextId.hideComponent();
                    binding.kitapTurListeRw.hideComponent();
                }else{
                    binding.kitapTurProgressBar.hideComponent();
                }
            }
        })
    }

    override fun destroyOthers() {
        adapter = null;
    }
}