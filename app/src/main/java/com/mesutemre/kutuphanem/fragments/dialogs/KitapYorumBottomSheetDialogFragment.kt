package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.databinding.KitapYorumBottomSheetDialogBinding
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.getCircleImageFromUrl
import com.mesutemre.kutuphanem.util.setDisplayMetricHeight

/**
 * @Author: mesutemre.celenk
 * @Date: 29.09.2021
 */
class KitapYorumBottomSheetDialogFragment(
    val kullanici:Kullanici
):BottomSheetDialogFragment() {

    private lateinit var binding:KitapYorumBottomSheetDialogBinding;

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
        binding.kybUserImage.let{
            it.getCircleImageFromUrl(kullanici.resim,it);
        }
        binding.kybCloseImageview.setOnClickListener {
            this.dismiss();
        }

    }
}