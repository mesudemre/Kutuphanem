package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.KitapYorumListeAdapter
import com.mesutemre.kutuphanem.adapters.KitapYorumListeHeaderAdapter
import com.mesutemre.kutuphanem.databinding.KitapYorumBottomSheetDialogBinding
import com.mesutemre.kutuphanem.model.*
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.setDisplayMetricHeight
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.KitapYorumViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: mesutemre.celenk
 * @Date: 29.09.2021
 */
@AndroidEntryPoint
class KitapYorumBottomSheetDialogFragment(
    val kullanici:Kullanici,
    val kitap:KitapModel
):BottomSheetDialogFragment() {

    private lateinit var binding:KitapYorumBottomSheetDialogBinding;
    private val viewModel: KitapYorumViewModel by viewModels();
    private var yorumListeHeaderAdapter:KitapYorumListeHeaderAdapter? = null;
    private var yorumListeAdapter:KitapYorumListeAdapter? = KitapYorumListeAdapter(listOf(),null);
    private var concatYorumAdapter:ConcatAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = KitapYorumBottomSheetDialogBinding.inflate(inflater);
        viewModel.getKitapYorumListe(kitap.kitapId!!);
        return binding.root;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme);
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { it ->
                    val behaviour = BottomSheetBehavior.from(it)
                    it.setDisplayMetricHeight(0.8);
                    behaviour.isDraggable = false;
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
        }
        return dialog;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.kybCloseImageview.setOnClickListener {
            this.dismiss();
        }
        observeKitapYorumListe();
    }

    private fun observeKitapYorumListe(){
        viewModel.kitapYorumListe.observe(viewLifecycleOwner, Observer {
           if(it.hasBeenHandled) {
               binding.kybYorumlarRecyclerView.layoutManager = LinearLayoutManager(context);
               yorumListeHeaderAdapter = KitapYorumListeHeaderAdapter(kullanici.resim,::kitapYorumKaydet);

               binding.kybYorumListeProgressBar.showComponent();
               if(it.hasBeenError){
                   binding.kybYorumListeProgressBar.hideComponent();
                   yorumListeAdapter = KitapYorumListeAdapter(it.peekContent(),context?.getString(R.string.kitapYorumListeHata));
               }else{
                   binding.kybYorumListeProgressBar.hideComponent();
                   if(it.peekContent().isEmpty()){
                       yorumListeAdapter = KitapYorumListeAdapter(it.peekContent(),context?.getString(R.string.kitapYorumListeBos));
                   }else{
                       yorumListeAdapter = KitapYorumListeAdapter(it.peekContent(),null);
                   }
               }
               concatYorumAdapter = ConcatAdapter(yorumListeHeaderAdapter,yorumListeAdapter);
               binding.kybYorumlarRecyclerView.adapter = concatYorumAdapter;
               it.hasBeenHandled = false;
           }
        });
    }

    fun kitapYorumKaydet(yorum:String){
        val kitapYorumModel = KitapYorumModel(null,kitap,
           yorum,null,null);
        viewModel.kitapYorumKaydet(kitapYorumModel);
        observeKitapYorumModel();
    }

    private fun observeKitapYorumModel(){
        viewModel.kitapYorumKayit.observe(viewLifecycleOwner, Observer {
            if(it.hasBeenHandled) {

                val response: ResponseStatusModel = it.peekContent();
                if(it.hasBeenError){
                    showSnackBar(binding.kybRootCordinator,response.statusMessage, ERROR);
                }else{
                    showSnackBar(binding.kybRootCordinator,response.statusMessage, SUCCESS);
                }
                it.hasBeenHandled = false;
                viewModel.getKitapYorumListe(kitap.kitapId!!);
                observeKitapYorumListe();
            }
        });
    }

    override fun onDestroy() {
        super.onDestroy();
        this.yorumListeHeaderAdapter = null;
        this.yorumListeAdapter = null;
        this.concatYorumAdapter = null;
        this.dismiss();
    }
}