package com.mesutemre.kutuphanem.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ProfilIslemFragmentBinding
import com.mesutemre.kutuphanem.fragments.dialogs.DogumTarihiDialogFragment
import com.mesutemre.kutuphanem.fragments.dialogs.ExitFromApplicationDialogFragment
import com.mesutemre.kutuphanem.fragments.dialogs.GuncellemeAlertDialogFragment
import com.mesutemre.kutuphanem.fragments.dialogs.ResimSecBottomSheetDialogFragment
import com.mesutemre.kutuphanem.listener.ProfilIslemFragmentClickListener
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.formatDate
import com.mesutemre.kutuphanem.util.getCircleImageFromUrl
import com.mesutemre.kutuphanem.util.setMotionVisibility
import com.mesutemre.kutuphanem.viewmodels.ProfilIslemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.profil_islem_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfilIslemFragment() :Fragment(), ProfilIslemFragmentClickListener {

    private val viewModel: ProfilIslemViewModel by viewModels();
    private var dataBinding: ProfilIslemFragmentBinding? = null;
    private lateinit var profilImage:ImageView;
    private lateinit var profilImageEnd:ImageView;
    private lateinit var jsonIlgiAlanListe:List<KitapturModel>;
    private var profilResimChanged:Boolean = false;
    private var selectedImageUri: Uri = Uri.EMPTY;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.profil_islem_fragment,container,false);
        dataBinding!!.profilClickListener = this;
        return dataBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getKullaniciBilgi();
        observeLiveData();
    }

    private fun observeLiveData(){
        viewModel.kullanici.observe(viewLifecycleOwner, Observer { kullanici->
            kullanici.let {
                dataBinding!!.kullanici = kullanici;
                profilImage = dataBinding!!.profilImage;
                profilImageEnd = dataBinding!!.profilImageEnd;
                profilImage.getCircleImageFromUrl(kullanici.resim,profilImage);
                profilImageEnd.getCircleImageFromUrl(kullanici.resim,profilImageEnd);
                observeKitapTurListe(kullanici);
            }
        });

        viewModel.kullaniciLoading.observe(viewLifecycleOwner, Observer {
            profilErrorTextId.visibility = View.GONE;
            if(it){
                profilProgressBar.visibility = View.VISIBLE;
                profilResimPanelLayoutId.setMotionVisibility(View.GONE);
                detayLayoutId.setMotionVisibility(View.GONE);
            }else{
                profilProgressBar.visibility = View.GONE;
                profilResimPanelLayoutId.setMotionVisibility(View.VISIBLE);
                detayLayoutId.setMotionVisibility(View.VISIBLE);
            }
        });

        viewModel.kullaniciError.observe(viewLifecycleOwner, Observer {
            profilProgressBar.visibility = View.GONE;
            if(it){
                profilErrorTextId.visibility = View.VISIBLE;
                profilResimPanelLayoutId.setMotionVisibility(View.GONE);
                detayLayoutId.setMotionVisibility(View.GONE);
            }else{
                profilErrorTextId.visibility = View.GONE;
                profilResimPanelLayoutId.setMotionVisibility(View.VISIBLE);
                detayLayoutId.setMotionVisibility(View.VISIBLE);
            }
        });
    }

    private fun observeKitapTurListe(kullanici: Kullanici?){
        viewModel.kitapTurListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe.let{
                this.jsonIlgiAlanListe = it;
                for (ia in it){
                    val ilgiAlan:Chip = Chip(requireContext());
                    ilgiAlan.text = ia.aciklama;
                    ilgiAlan.setChipBackgroundColorResource(R.color.white);
                    ilgiAlan.setChipStrokeColorResource(R.color.black);
                    ilgiAlan.chipStrokeWidth = 1f;
                    ilgiAlan.setTextColor(requireContext().getColor(R.color.black));
                    ilgiAlan.isCheckable = true;
                    ilgiAlan.setCheckedIconResource(R.drawable.ic_baseline_check_circle_outline_24);

                    if(kullanici!!.ilgiAlanlari != null && kullanici!!.ilgiAlanlari!!.size>0){
                        if(isIlgiAlanSecili(kullanici.ilgiAlanlari!!,ia)){
                            ilgiAlan.isChecked = true;
                        }
                    }
                    ilgiAlanChips.addView(ilgiAlan);
                }
            }
        });

        viewModel.kitapTurLoading.observe(viewLifecycleOwner, Observer {
            ilgiAlanHataTextViewId.visibility = View.GONE;
            if(it){
                ilgiAlanProgressBarId.visibility = View.VISIBLE;
                ilgiAlanChips.visibility = View.GONE;
            }else{
                ilgiAlanProgressBarId.visibility = View.GONE;
                ilgiAlanChips.visibility = View.VISIBLE;
            }
        });

        viewModel.kitapTurError.observe(viewLifecycleOwner, Observer {
            ilgiAlanProgressBarId.visibility = View.GONE;
            if(it){
                ilgiAlanHataTextViewId.visibility = View.VISIBLE;
                ilgiAlanChips.visibility = View.GONE;
            }else{
                ilgiAlanHataTextViewId.visibility = View.GONE;
                ilgiAlanChips.visibility = View.VISIBLE;
            }
        });
    }

    private fun isIlgiAlanSecili(ilgiAlanListe:List<KitapturModel>,ilgiliAlan:KitapturModel):Boolean{
        for (ia in ilgiAlanListe){
            if(ia.kitapTurId == ilgiliAlan.kitapTurId){
                return true;
            }
        }
        return false;
    }

    override fun openDatePicker(view: View, dogumTarihi: Date) {
        val editText = view as TextInputEditText;
        DogumTarihiDialogFragment(editText,dogumTarihi).show(requireFragmentManager(),null);
    }

    override fun openProfilResimDegistirme(view: View,profilImage:View) {
        val profilResimChange = ResimSecBottomSheetDialogFragment(
            profilImage as ImageView,
            profilImageEnd,
            view.context);
        var resimChangeBundle:Bundle = Bundle();
        profilResimChange.arguments = resimChangeBundle;
        profilResimChange.show(this.childFragmentManager,"");
    }

    override fun kullaniciGuncelle(view: View) {
        if(dataBinding!!.kullanici?.ad == null || dataBinding!!.kullanici?.ad?.length == 0){
            dataBinding!!.textInputProfilAd.error = view.context.getString(R.string.adValidationErr);
            return;
        }
        if(dataBinding!!.kullanici?.soyad == null || dataBinding!!.kullanici?.soyad?.length == 0){
            dataBinding!!.textInputProfilSoyad.error = view.context.getString(R.string.soyadValidationErr);
            return;
        }
        if(dataBinding!!.kullanici?.eposta == null || dataBinding!!.kullanici?.eposta?.length == 0){
            dataBinding!!.textInputProfilEposta.error = view.context.getString(R.string.epostaValidationErr);
            return ;
        }

        if(dataBinding!!.kullanici?.eposta != null && !dataBinding!!.kullanici?.eposta?.contains("@")!!){
            dataBinding!!.textInputProfilEposta.error = view.context.getString(R.string.epostaFormatValidationErr);
            return ;
        }

        val jsonObj:JSONObject = JSONObject();

        val ids = ilgiAlanChips.checkedChipIds;
        val iaArr:JSONArray = JSONArray();
        var iaJsonObj:JSONObject = JSONObject();
        for (i in ids){
            val chip:Chip = ilgiAlanChips.findViewById(i);
            val id:Int = getSelectedChipId(chip.text.toString());
            iaJsonObj.put("id",id);
            iaArr.put(iaJsonObj);
            iaJsonObj = JSONObject();
        }

        jsonObj.put("ilgiAlanlari",iaArr);
        jsonObj.put("username",dataBinding!!.kullanici?.username);
        jsonObj.put("ad",dataBinding!!.kullanici?.ad);
        jsonObj.put("soyad",dataBinding!!.kullanici?.soyad);
        jsonObj.put("eposta",dataBinding!!.kullanici?.eposta);
        jsonObj.put("haberdarmi",dataBinding!!.kullanici?.haberdarmi);
        jsonObj.put("dogumTarihi",
            dataBinding!!.kullanici?.dogumTarihi?.let { formatDate(it,"yyyy-MM-dd") });
        jsonObj.put("cinsiyet","E");
        if(dataBinding!!.kullanici?.cinsiyet?.value.equals("KADIN")){
            jsonObj.put("cinsiyet","K");
        }

        GuncellemeAlertDialogFragment(jsonObj.toString(),
            viewModel,
            viewLifecycleOwner,
            profilProgressBar,
            view,
            detayLayoutId,
            profilResimChanged,
            selectedImageUri!!,
            dataBinding!!.kullanici!!.username).show(requireFragmentManager(),null);
    }

    private fun getSelectedChipId(chipText:String):Int{
        for (ia in this.jsonIlgiAlanListe){
            if(ia.aciklama.equals(chipText)){
                return ia.kitapTurId!!;
            }
        }
        return 0;
    }

    override fun logoutFromApplication(view: View) {
        ExitFromApplicationDialogFragment(customSharedPreferences,this).show(requireFragmentManager(),null);
    }

    fun setProfilResimChanged(profilResimChanged:Boolean){
        this.profilResimChanged = profilResimChanged;
    }

    fun setSelectedImageUri(selectedImageUri:Uri){
        this.selectedImageUri = selectedImageUri;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding = null;
    }
}