package com.mesutemre.kutuphanem.job

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.exceptions.dao.KutuphanemGlobalExceptionHandlerDao
import com.mesutemre.kutuphanem.exceptions.model.KutuphanemGlobalExceptionHandlerModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

/**
 * @Author: mesutemre.celenk
 * @Date: 17.01.2022
 */
@HiltWorker
class GlobalExceptionCatchWorker  @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val workerParams: WorkerParameters,
    private val exceptionHandlerDao: KutuphanemGlobalExceptionHandlerDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Worker(appContext, workerParams){

    private lateinit var defaultUEH: Thread.UncaughtExceptionHandler
    val scope = CoroutineScope(ioDispatcher);

    private val handler = Thread.UncaughtExceptionHandler { thread, throwable ->
        scope.launch {
            val exceptionHandlerModel =
                KutuphanemGlobalExceptionHandlerModel(throwable.stackTraceToString());
            exceptionHandlerDao.exceptionKaydet(exceptionHandlerModel)
            defaultUEH.uncaughtException(thread,throwable);
            exitProcess(1);
        }
    }
    override fun doWork(): Result {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
        return Result.success();
    }
}