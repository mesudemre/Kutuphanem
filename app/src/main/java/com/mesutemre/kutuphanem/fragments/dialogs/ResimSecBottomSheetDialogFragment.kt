package com.mesutemre.kutuphanem.fragments.dialogs

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.fragments.ProfilIslemFragment
import com.mesutemre.kutuphanem.util.*
import kotlinx.android.synthetic.main.resim_sec_bottom_sheet_dialog_fragment.*

class ResimSecBottomSheetDialogFragment(
    val profilImageView: ImageView,
    val profilImageEnd:ImageView,
    val ctx:Context
):BottomSheetDialogFragment() {

    private var kameraIzin:Int = 0;
    private var readExternalStorageIzin:Int = 0;
    private lateinit var camContext: Context;
    private lateinit var readExtContext: Context;
    private var selectedImageUri:Uri = Uri.EMPTY;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.resim_sec_bottom_sheet_dialog_fragment,container,false);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        cameraLayoutId.setOnClickListener {
            camContext = ctx;
            kameraIzin = ContextCompat.checkSelfPermission(ctx,Manifest.permission.CAMERA);
            if(kameraIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE);
            }else{
                val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        }

        galeriLayoutId.setOnClickListener {
            readExtContext = ctx;
            readExternalStorageIzin = ContextCompat.checkSelfPermission(view.context,Manifest.permission.READ_EXTERNAL_STORAGE);
            if(readExternalStorageIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),READ_EXTERNAL_STORAGE_REQUEST_CODE);
            }else{
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        }

        removeImageLayoutId.setOnClickListener {
            profilImageView.getCircleImageFromResource(R.drawable.ic_baseline_person_24,profilImageView);
            profilImageEnd.getCircleImageFromResource(R.drawable.ic_baseline_person_24,profilImageEnd);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_REQUEST_CODE){
            kameraIzin = ContextCompat.checkSelfPermission(camContext,Manifest.permission.CAMERA);
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        }else if(requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE){
            readExternalStorageIzin = ContextCompat.checkSelfPermission(readExtContext,Manifest.permission.READ_EXTERNAL_STORAGE);
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val photo: Bitmap = data!!.getExtras()?.get("data") as Bitmap;
            profilImageView.getCircleImageFromBitmap(photo,profilImageView);
            profilImageEnd.getCircleImageFromBitmap(photo,profilImageEnd);
            selectedImageUri = getImageUriFromBitmap(ctx,photo);
        }else if(requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val uri: Uri = data?.data!!;
            profilImageView.getCircleImageFromUri(uri,profilImageView);
            profilImageEnd.getCircleImageFromUri(uri,profilImageEnd);
            selectedImageUri = uri;
        }
        val fr = parentFragment as ProfilIslemFragment;
        fr.setProfilResimChanged(true);
        fr.setSelectedImageUri(selectedImageUri);
        dismiss();
    }
}