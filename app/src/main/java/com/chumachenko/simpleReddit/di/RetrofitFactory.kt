package com.chumachenko.simpleReddit.di

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun createApiRetrofit(
            gson: Gson,
            baseEndpoint: String,
            okHttpClient: OkHttpClient
    ): Retrofit {
        val converterFactory = converterFactory(gson)
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(baseEndpoint)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun converterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }
}