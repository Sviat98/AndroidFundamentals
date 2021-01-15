package com.bashkevich.androidfundamentals.model

import com.bashkevich.androidfundamentals.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.create



object RetrofitModule {
    const val BASE_URL ="https://api.themoviedb.org/3/"
    const val IMAGES_BASE_URL ="https://image.tmdb.org/t/p/w342"
    private val serialization = Json {
        ignoreUnknownKeys = true
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(
        serialization.asConverterFactory(
            "application/json".toMediaType()
        )
    ).build()

    val moviesApi = retrofit.create<MoviesApi>()
}

class ApiKeyInterceptor : Interceptor{
    private val API_KEY_NAME = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()


        val newUrl: HttpUrl = originalRequest.url.newBuilder().addQueryParameter(API_KEY_NAME, BuildConfig.MOVIE_DATABASE_API_KEY).build()

        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }

}
