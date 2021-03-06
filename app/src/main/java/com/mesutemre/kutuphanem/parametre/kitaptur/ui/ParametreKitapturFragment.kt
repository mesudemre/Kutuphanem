package com.mesutemre.kutuphanem.parametre.kitaptur.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.parametre.kitaptur.adapter.KitapTurAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ParametreKitapturFragmentBinding
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.parametre.ui.ParametreFragmentDirections
import com.mesutemre.kutuphanem.util.*
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
    private var adapter: KitapTurAdapter? = null;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        val token = customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY);
        adapter = KitapTurAdapter(arrayListOf(),token,::deleteKitapTur);
        viewModel.kitapTurListeGetir(false);
        binding.kitapTurListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapTurListeRw.adapter = adapter;

        observeLiveData();

        binding.kitapTurSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapTurProgressBar.showComponent();
            getFragmentView().hideComponents(binding.kitapTurErrorTextId,binding.kitapTurListeRw)

            binding.kitapTurSwipeRefreshLayout.isRefreshing = false;
            viewModel.kitapTurListeGetir(true);
        }

        kitapTurEkleFloatingActionButton.setOnClickListener {
            val action = ParametreFragmentDirections.actionParametreFragmentToParametreEklemeFragment("kitaptur");
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeLiveData(){
        viewModel.kitapTurListeResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapTurProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.kitapTurErrorTextId,binding.kitapTurListeRw);
                }
                is BaseResourceEvent.Error->{
                    binding.kitapTurErrorTextId.showComponent();
                    getFragmentView().hideComponents(binding.kitapTurProgressBar,binding.kitapTurListeRw);
                }
                is BaseResourceEvent.Success->{
                    getFragmentView().hideComponents(binding.kitapTurProgressBar,binding.kitapTurErrorTextId);
                    binding.kitapTurListeRw.showComponent();
                    adapter?.updateKitapTurListe(it.data!!);
                }
            }
        });
    }

    private fun deleteKitapTur(jsonStr:String) {
        viewModel.deleteKitapturParametre(jsonStr);
        observeKitapturSil();
    }

    private fun observeKitapturSil() {
        viewModel.kitapTurSilResourceEvent.observe(viewLifecycleOwner, Observer {
           when(it){
               is BaseResourceEvent.Loading->{
                   getFragmentView().hideComponents(binding.kitapTurErrorTextId,binding.kitapTurListeRw);
                   binding.kitapTurProgressBar.showComponent();
               }
               is BaseResourceEvent.Error->{
                   binding.kitapTurProgressBar.hideComponent();
                   showSnackBar(getFragmentView(),getFragmentView().context.resources.getString(R.string.kitapTurSilmeHata), ERROR);
               }
               is BaseResourceEvent.Success->{
                   binding.kitapTurProgressBar.hideComponent();
                   showSnackBar(getFragmentView(),it.data!!.statusMessage, SUCCESS);
                   viewModel.kitapTurListeGetir(false);
                   observeLiveData();
               }
           }
        });
    }

    override fun destroyOthers() {
        adapter = null;
    }
}