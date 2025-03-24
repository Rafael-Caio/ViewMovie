package com.rafaeltech.viewmovie.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"


        // Instância única do Retrofit usando lazy
        private val retrofit: Retrofit by lazy {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        //Tipo Retrofit que é igual ao retrofit do lazy
        fun getInstance(): Retrofit = retrofit
    }
}
