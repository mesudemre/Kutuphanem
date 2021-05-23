package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mesutemre.kutuphanem.R
import org.json.JSONObject

class GuncellemeAlertDialogFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext());
        ad.setMessage(requireContext().getString(R.string.guncelleAlertMessage));
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->

        }
        ad.setNegativeButton(requireContext().getString(R.string.hayir)){dialogInterface, i ->
        }
        return ad.create();
    }
    override fun onDestroy() {
        super.onDestroy();
        this.dismiss();
    }

}