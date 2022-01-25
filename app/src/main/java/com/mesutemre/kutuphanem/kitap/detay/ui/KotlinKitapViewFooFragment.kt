package com.mesutemre.kutuphanem.kitap.detay.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.FragmentKotlinFooBinding
import java.io.File
import java.util.*

/**
 * @Author: mesutemre.celenk
 * @Date: 21.01.2022
 */
class KotlinKitapViewFooFragment: BaseFragment<FragmentKotlinFooBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKotlinFooBinding
            = FragmentKotlinFooBinding::inflate
    override val layoutName: String = "fragment_kitap_detay.xml"

    private lateinit var file:File

    private val args:KotlinKitapViewFooFragmentArgs by navArgs()

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        try {
            binding.pdfView.fromBytes(args.fileInfo.fileByteArray).load()
        }catch (e:Exception) {
            Log.d("HATA","hata meydana geldi!!!")
            e.printStackTrace()
        }
        /*val directory = File(context?.filesDir,"kitapdownloads");
        if (directory.exists()) {
            val files = directory.listFiles()
            file = files.get(0)
            binding.pdfView.fromFile(file).load()
        }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Arrays.fill(args.fileInfo.fileByteArray,0)
        Log.d("Dosya","Dosya silindi.....")
    }
}