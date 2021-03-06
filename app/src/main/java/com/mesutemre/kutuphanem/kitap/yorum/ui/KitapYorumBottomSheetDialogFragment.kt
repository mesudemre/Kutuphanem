package com.mesutemre.kutuphanem.kitap.yorum.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.KitapYorumBottomSheetDialogBinding
import com.mesutemre.kutuphanem.kitap.detay.ui.KitapDetayViewModel
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.yorum.adapter.KitapYorumListeAdapter
import com.mesutemre.kutuphanem.kitap.yorum.adapter.KitapYorumListeHeaderAdapter
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapPuanModel
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapYorumModel
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.setDisplayMetricHeight
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.util.showSnackBar

/**
 * @Author: mesutemre.celenk
 * @Date: 29.09.2021
 */

class KitapYorumBottomSheetDialogFragment(
    val kullanici: Kullanici,
    val kitap: KitapModel,
    val viewModel: KitapDetayViewModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: KitapYorumBottomSheetDialogBinding;
    private var yorumListeHeaderAdapter: KitapYorumListeHeaderAdapter? = null;
    private var yorumListeAdapter: KitapYorumListeAdapter? = KitapYorumListeAdapter(listOf(), null);
    private var concatYorumAdapter: ConcatAdapter? = null;

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
        binding.kitapYorumListeSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapYorumListeSwipeRefreshLayout.isRefreshing = false;
            viewModel.getKitapYorumListe(kitap.kitapId!!);
        }
        observeKitapYorumListe();
    }

    private fun observeKitapYorumListe() {
        viewModel.kitapYorumListe.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kybYorumListeProgressBar.showComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kybYorumListeProgressBar.hideComponent();
                    yorumListeAdapter = KitapYorumListeAdapter(
                        it.data!!,
                        context?.getString(R.string.kitapYorumListeHata)
                    );
                }
                is BaseResourceEvent.Success->{
                    binding.kybYorumListeProgressBar.hideComponent();
                    binding.kybYorumlarRecyclerView.layoutManager = LinearLayoutManager(context);
                    yorumListeHeaderAdapter = KitapYorumListeHeaderAdapter(
                        kullanici.resim,
                        ::kitapYorumKaydet,
                        ::kitapPuanKaydet
                    );
                    if(it.data != null && it.data.isEmpty()) {
                        yorumListeAdapter = KitapYorumListeAdapter(
                            it.data!!,
                            context?.getString(R.string.kitapYorumListeBos)
                        );
                    }else {
                        yorumListeAdapter = KitapYorumListeAdapter(it.data!!, null);
                    }
                    concatYorumAdapter = ConcatAdapter(yorumListeHeaderAdapter, yorumListeAdapter);
                    binding.kybYorumlarRecyclerView.adapter = concatYorumAdapter;
                }
            }
        });
    }

    fun kitapYorumKaydet(yorum: String) {
        val kitapYorumModel = KitapYorumModel(
            null, kitap,
            yorum, null, null
        );
        viewModel.kitapYorumKaydet(kitapYorumModel);
        observeKitapYorumModel();
    }

    fun kitapPuanKaydet(puan: Float) {
        val kitapPuanModel = KitapPuanModel(kitap,puan.toInt());
        viewModel.kitapPuanKaydet(kitapPuanModel);
        observeKitapPuanKayit();
    }

    private fun observeKitapYorumModel() {
        viewModel.kitapYorumKayit.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Error->{
                    showSnackBar(binding.kybRootCordinator, it.message!!, ERROR);
                }
                is BaseResourceEvent.Success->{
                    showSnackBar(binding.kybRootCordinator, it.data!!.statusMessage, SUCCESS);
                    viewModel.getKitapYorumListe(kitap.kitapId!!);
                    observeKitapYorumListe();
                }
            }
        });
    }

    private fun observeKitapPuanKayit() {
        viewModel.kitapPuanKayit.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Error->{
                    showSnackBar(binding.kybRootCordinator, it.message!!, ERROR);
                }
                is BaseResourceEvent.Success->{
                    showSnackBar(binding.kybRootCordinator, it.data!!.statusMessage, SUCCESS);
                }
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