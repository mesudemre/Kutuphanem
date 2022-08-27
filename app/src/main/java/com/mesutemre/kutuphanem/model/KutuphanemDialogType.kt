package com.mesutemre.kutuphanem.model

import androidx.annotation.IntDef

@IntDef(QA_DLG,WARNING_DLG,INFO_DLG,SUCCESS_DLG,ERROR_DLG)
@Retention(AnnotationRetention.SOURCE)
annotation class KutuphanemDialogType

const val QA_DLG:Int  = 1
const val WARNING_DLG:Int  = 2
const val INFO_DLG:Int  = 3
const val SUCCESS_DLG:Int  = 4
const val ERROR_DLG:Int = 5
