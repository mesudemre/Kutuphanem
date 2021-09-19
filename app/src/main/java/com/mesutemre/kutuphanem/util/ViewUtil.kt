package com.mesutemre.kutuphanem.util

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .error(R.mipmap.kutuphanem_icon_round);
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .circleCrop()
        .into(this);
}

fun ImageView.getCircleImageFromBitmap(photo: Bitmap?, iv: ImageView){
    Glide.with(iv.context)
        .asBitmap()
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromUri(photo: Uri?, iv: ImageView){
    Glide.with(iv.context)
        .load(photo)
        .circleCrop()
        .into(iv);
}

fun ImageView.getCircleImageFromResource(resourceId:Int?, iv: ImageView){
    Glide.with(iv.context)
        .load(resourceId)
        .circleCrop()
        .into(iv);
}

fun TextInputLayout.writeFormatDate(date: Date?, component: TextInputLayout){
    val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
    if(date != null){
        component.editText?.setText(sdf.format(date));
    }else{
        component.editText?.setText("01.01.2021");
    }
}

@BindingAdapter(value = ["android:downloadUrl"])
fun downloadImage(view: ImageView, url:String?){
    view.getImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:circleDownloadUrl"])
fun downloadCirclerImage(view: ImageView, url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:bitMapImageLoad"])
fun loadCircleBitmapImageLoad(view: ImageView, photo: Bitmap?){
    view.getCircleImageFromBitmap(photo,view);
}

@BindingAdapter(value = ["android:circleImageLoad"])
fun loadCircleImage(view: ImageView, url:String?){
    view.getCircleImageFromUrl(url,view);
}

@BindingAdapter(value = ["android:writeDateStringValue"])
fun writeDateStrValue(view: TextInputLayout, date: Date?){
    view.writeFormatDate(date,view);
}

@BindingAdapter(value = ["android:clearStringValidation"])
fun clearStringValidation(view: TextInputLayout, value:String?){
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

fun View.showComponent(){
    this.visibility = View.VISIBLE;
}

fun View.hideComponent(){
    this.visibility = View.GONE;
}

fun TextInputEditText.hideKeyboard(et: TextInputEditText){
    val imm = ContextCompat.getSystemService(et.context, InputMethodManager::class.java);
    imm?.hideSoftInputFromWindow(et.windowToken, 0);
}

fun TextInputEditText.clearContent(et: TextInputEditText){
    et.editableText.clear();
}

@BindingAdapter(value = ["android:loadFromLocal"])
fun loadImageFromLocal(view: ImageView, kitapId:Int){
    view.getImageFromLocal(kitapId,view);
}

fun ImageView.getImageFromLocal(kitapId: Int, view: ImageView){
    var path:String = Environment.getExternalStorageDirectory().path;
    path = path+"/Android/media/com.mesutemre.kutuphanem/Kütüphanem/${kitapId.toString()}.png";
    val f = File(path);
    if(f.exists()){
        view.setImageBitmap(BitmapFactory.decodeFile(path));
    }else{
        Glide.with(view.context)
            .load(R.mipmap.kutuphanem_icon_round)
            .into(view);
    }
}

fun ImageView.setTint(@ColorInt color: Int?) {
    if (color == null) {
        ImageViewCompat.setImageTintList(this, null)
        return
    }
    ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

@BindingAdapter(value = ["android:formatTarih"])
fun writeFormatTarih(view:MaterialTextView,tarih:Date){
    view.text = formatDate(tarih,"dd.MM.yyyy");
}