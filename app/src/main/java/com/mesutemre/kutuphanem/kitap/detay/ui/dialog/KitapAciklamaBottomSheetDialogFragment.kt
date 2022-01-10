package com.mesutemre.kutuphanem.kitap.detay.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.databinding.KitapAciklamaBottomSheetDialogFragmentBinding
import com.mesutemre.kutuphanem.util.setDisplayFullHeight

/**
 * @Author: mesutemre.celenk
 * @Date: 26.09.2021
 */
class KitapAciklamaBottomSheetDialogFragment(val kitapAciklama:String): BottomSheetDialogFragment() {

    private lateinit var binding:KitapAciklamaBottomSheetDialogFragmentBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = KitapAciklamaBottomSheetDialogFragmentBinding.inflate(inflater);
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
                it.setDisplayFullHeight();
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.kitapDetayAciklamaTextDialogId.text = kitapAciklama;
        binding.closeKitapAciklamaDlgImageViewId.setOnClickListener {
            dismiss();
        }
    }
}