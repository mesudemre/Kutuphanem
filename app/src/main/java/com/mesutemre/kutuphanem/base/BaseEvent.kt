package com.mesutemre.kutuphanem.base

/**
 * @Author: mesutemre.celenk
 * @Date: 31.08.2021
 */
open class BaseEvent<out T>(private val content:T?) {

    var hasBeenHandled  = false;
    var hasBeenError    = false;
    fun peekContent():T = content!!;
}