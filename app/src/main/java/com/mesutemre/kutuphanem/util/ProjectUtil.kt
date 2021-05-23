package com.mesutemre.kutuphanem.util

import android.graphics.Bitmap
import android.net.Uri
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import kotlinx.android.synthetic.main.layout_login.*
import java.text.SimpleDateFormat
import java.util.*

const val APP_TOKEN_KEY:String = "APP_TOKEN";
const val KULLANICI_ADI_KEY:String = "KULLANICI_ADI";
const val API_URL:String = "http://192.168.1.106:8080/KutuphaneSistemiWS/";
const val KUTUPHANEM_DB_NAME = "kutuphanem";
const val PARAM_YAYINEVI_DB_KEY:String = "PARAM_YAYINEVI";
const val PARAM_KITAPTUR_DB_KEY:String = "PARAM_KITAPTUR";
const val KULLANICI_DB_MEVCUT:String = "KULLANICI_MEVCUT";
const val CAMERA_REQUEST_CODE:Int = 1991;
const val READ_EXTERNAL_STORAGE_REQUEST_CODE:Int=1992;

fun ImageView.getImageFromUrl(url:String?, iv: ImageView){
    val circularProgressDrawable = CircularProgressDrawable(iv.context);
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.start()
    val options = RequestOptions()
            .placeholder(circularProgressDrawable)
            .error(R.mipmap.kutuphanem_icon_round);
    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this);
}

fun ImageView.getCircleImageFromUrl(url:String?, iv: ImageView){
    val circularProgressDrawable = CircularProgressDrawable(iv.context);
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.start()
    val options = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.kutuphanem_icon_round);
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .circleCrop()
        .into(this);
}

fun ImageView.getCircleImageFromBitmap(photo:Bitmap?,iv:ImageView){
    Glide.with(iv.context)
        .asBitmap()
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromUri(photo:Uri?,iv:ImageView){
    Glide.with(iv.context)
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromResource(resourceId:Int?,iv:ImageView){
    Glide.with(iv.context)
        .load(resourceId)
        .circleCrop()
        .into(iv);
}

fun TextInputLayout.writeFormatDate(date:Date?,component:TextInputLayout){
    val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
    if(date != null){
        component.editText?.setText(sdf.format(date));
    }else{
        component.editText?.setText("01.01.2021");
    }
}

@BindingAdapter(value = ["android:downloadUrl"])
fun downloadImage(view:ImageView,url:String?){
    view.getImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:circleDownloadUrl"])
fun downloadCirclerImage(view:ImageView,url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:bitMapImageLoad"])
fun loadCircleBitmapImageLoad(view:ImageView,photo:Bitmap?){
   view.getCircleImageFromBitmap(photo,view);
}

@BindingAdapter(value = ["android:circleImageLoad"])
fun loadCircleImage(view:ImageView,url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:writeDateStringValue"])
fun writeDateStrValue(view:TextInputLayout,date:Date?){
    view.writeFormatDate(date,view);
}

@BindingAdapter(value = ["android:emptyStringValidation"])
fun emptyStringValidation(view:TextInputLayout,value:String?){
    view.editText!!.addTextChangedListener(TextInputErrorClearListener(view));
}

fun View.setMotionVisibility(visibility: Int) {
    val motionLayout = parent as MotionLayout
    motionLayout.constraintSetIds.forEach {
        val constraintSet = motionLayout.getConstraintSet(it) ?: return@forEach
        constraintSet.setVisibility(this.id, visibility)
        constraintSet.applyTo(motionLayout)
    }
}



