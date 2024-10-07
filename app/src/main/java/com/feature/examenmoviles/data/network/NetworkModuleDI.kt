package com.feature.examenmoviles.data.network

import com.feature.examenmoviles.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModuleDI {

    fun provideDragonBallApi(): DragonBallAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DragonBallAPIService::class.java)
    }
}