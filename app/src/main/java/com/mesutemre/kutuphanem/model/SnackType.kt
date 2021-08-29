package com.mesutemre.kutuphanem.model

import androidx.annotation.IntDef

@IntDef(SUCCESS, WARNING, ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class SnackType

const val SUCCESS:Int  = 1;
const val WARNING:Int  = 2;
const val ERROR:Int    = 0;