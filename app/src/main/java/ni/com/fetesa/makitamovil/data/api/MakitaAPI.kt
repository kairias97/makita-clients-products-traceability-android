package ni.com.fetesa.makitamovil.data.api

/**
 * Created by dusti on 08/05/2018.
 */

import ni.com.fetesa.makitamovil.utils.MAKITA_MAIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MakitaAPI {
    val service: IMakitaService?

    private constructor(){
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(MAKITA_MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(IMakitaService::class.java)
    }
    companion object {
        val instance: MakitaAPI by lazy { MakitaAPI() }
    }
}