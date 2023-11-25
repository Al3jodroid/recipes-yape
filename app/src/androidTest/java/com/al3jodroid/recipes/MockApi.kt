package com.al3jodroid.recipes

import com.al3jodroid.recipes.client.api.RecipeRetrofitApi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader

open class MockApi {
    val mockServer = MockWebServer()
    lateinit var recipeRetrofitApi: RecipeRetrofitApi

    @Throws(Exception::class)
    fun initMockApi() {
        mockServer.start()
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockServer.url("/")).build()
        recipeRetrofitApi = RecipeRetrofitApi(retrofit)
    }

    @Throws(Exception::class)
    fun buildResponse(path: String): String {
        val stringBuilder = StringBuilder("")
        val bufferedReader = BufferedReader(
            InputStreamReader(javaClass.getResource("/$path")!!.openStream())
        )
        bufferedReader.use { stringBuilder.append(it.readText()) }

        //emulate time response ( help to avoid null exceptions in the tests running)
        Thread.sleep(200)
        return stringBuilder.toString()
    }
}