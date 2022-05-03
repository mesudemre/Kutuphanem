package com.mesutemre.kutuphanem.model

import androidx.annotation.StringRes

data class ValidationResult(
    val successfullValidate:Boolean,
    @StringRes val messageResId:Int? = null
)
