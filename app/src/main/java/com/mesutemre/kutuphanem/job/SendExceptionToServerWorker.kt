package com.mesutemre.kutuphanem.job

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mesutemre.kutuphanem.auth.service.KullaniciService
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.exceptions.dao.KutuphanemGlobalExceptionHandlerDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @Author: mesutemre.celenk
 * @Date: 17.01.2022
 */
@HiltWorker
class SendExceptionToServerWorker  @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val workerParams: WorkerParameters,
    private val exceptionHandlerDao: KutuphanemGlobalExceptionHandlerDao,
    private val kullaniciService: KullaniciService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
        return Result.success();
    }
}