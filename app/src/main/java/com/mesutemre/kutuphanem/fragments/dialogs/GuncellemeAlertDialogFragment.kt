package com.mesutemre.kutuphanem.fragments.dialogs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.SnackTypeEnum
import com.mesutemre.kutuphanem.util.setMotionVisibility
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewmodels.ProfilIslemViewModel

class GuncellemeAlertDialogFragment(val jsonStr:String,
                                    val viewModel: ProfilIslemViewModel,
                                    val lifeCycleOwner: LifecycleOwner,
                                    val progresBar:ProgressBar,
                                    val mevcutView:View,
                                    val detayLayoutId:NestedScrollView,
                                    val profilResimChanged:Boolean,
                                    val selectedImageUri:Uri,
                                    val username:String): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ad = AlertDialog.Builder(requireContext());
        ad.setMessage(requireContext().getString(R.string.guncelleAlertMessage));
        ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
            viewModel.kullaniciBilgiUpdate(jsonStr,profilResimChanged,selectedImageUri,username,mevcutView.context);
            observeProfilGuncellemeIslemLiveData();
        }
        ad.setNegativeButton(requireContext().getString(R.string.hayir)){dialogInterface, i ->
        }
        return ad.create();
    }
    override fun onDestroy() {
        super.onDestroy();
        this.dismiss();
    }

    private fun observeProfilGuncellemeIslemLiveData(){
        viewModel.kullaniciGuncelleSonuc.observe(lifeCycleOwner, Observer { response->
            response.let {
                if(it.statusCode.equals("200")){
                    showSnackBar(mevcutView,response.statusMessage,SnackTypeEnum.SUCCESS);
                }else{
                    showSnackBar(mevcutView,response.statusMessage,SnackTypeEnum.ERROR);
                }
            }
        });

        viewModel.kullaniciGuncelleLoading.observe(lifeCycleOwner,Observer{it->
            if(it){
                progresBar.visibility = View.VISIBLE;
                detayLayoutId.setMotionVisibility(View.INVISIBLE);
            }else{
                progresBar.visibility = View.GONE;
                detayLayoutId.setMotionVisibility(View.VISIBLE);
            }
        });

        viewModel.kullaniciGuncelleError.observe(lifeCycleOwner,Observer{it->
            progresBar.visibility = View.GONE;
            showSnackBar(mevcutView,mevcutView.context.getString(R.string.profilGuncellemeSunucuHata),SnackTypeEnum.ERROR)
        });
    }

}