package com.chumachenko.simpleReddit.data.api

import com.chumachenko.simpleReddit.GlobalConstants.BASE_ENDPOINT
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    fun getInstance(): Retrofit = Retrofit.Builder().baseUrl(BASE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}