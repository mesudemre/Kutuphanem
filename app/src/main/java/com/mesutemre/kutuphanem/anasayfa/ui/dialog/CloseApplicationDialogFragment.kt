package com.mesutemre.kutuphanem.anasayfa.ui.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mesutemre.kutuphanem.R

/**
 * @Author: mesutemre.celenk
 * @Date: 9.09.2021
 */
class CloseApplicationDialogFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext());
        ad.setMessage(requireContext().getString(R.string.closeAppLabel))
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
            val isLollipopOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
            if(isLollipopOrAbove){
                parentFragment?.activity?.finishAndRemoveTask()
            }else{
                parentFragment?.activity?.finishAffinity()
            }
        }
        ad.setNegativeButton(requireContext().getString(R.string.hayir)){ dialogInterface, i ->
        }
        return ad.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.dismiss()
    }
}