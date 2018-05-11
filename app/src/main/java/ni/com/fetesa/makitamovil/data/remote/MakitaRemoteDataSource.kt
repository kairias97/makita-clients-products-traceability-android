package ni.com.fetesa.makitamovil.data.remote

import android.support.compat.BuildConfig
import android.util.Log
import ni.com.fetesa.makitamovil.data.api.MakitaAPI
import ni.com.fetesa.makitamovil.model.*
import ni.com.fetesa.makitamovil.data.remote.Callbacks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by dusti on 09/05/2018.
 */
class MakitaRemoteDataSource {
    private constructor()

    fun validateMakitaCredentials(data: LoginRequest, callback: IMakitaValidateCredentialsCallBack){
        val authCall = MakitaAPI.instance.service!!.validateMakitaCredentials(data)
        authCall.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)
                    401 -> callback.onUnauthorized(response.body()!!)

                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaLoginError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaLoginError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    fun registerToMakita(data: RegistrationsRequest, callback: IMakitaRegistrationCallBack){
        val authCall = MakitaAPI.instance.service!!.registerToMakita(data)
        authCall.enqueue(object: Callback<RegistrationResponse>{
            override fun onResponse(call: Call<RegistrationResponse>?, response: Response<RegistrationResponse>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)

                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaRegistrationError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaRegistrationError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    fun verifyRegistration(data: RegistrationVerificationRequest, callback: IMakitaRegistrationVerificationCallBack){
        val authCall = MakitaAPI.instance.service!!.verifyRegistration(data)
        authCall.enqueue(object: Callback<RegistrationVerificationResponse>{
            override fun onResponse(call: Call<RegistrationVerificationResponse>?, response: Response<RegistrationVerificationResponse>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)

                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaRegistrationVerificationError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<RegistrationVerificationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaRegistrationVerificationError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    fun completeRegistration(data: RegistrationCompletionRequest, callback: IMakitaRegistrationCompletionCallBack){
        val authCall = MakitaAPI.instance.service!!.completeRegistration(data)
        authCall.enqueue(object: Callback<RegistrationCompletionResponse>{
            override fun onResponse(call: Call<RegistrationCompletionResponse>?, response: Response<RegistrationCompletionResponse>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaRegistrationCompletionError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<RegistrationCompletionResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaRegistrationCompletionError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    fun getProfile(token: String, callback: IMakitaGetProfileCallBack){
        val authCall = MakitaAPI.instance.service!!.getProfile(token)
        authCall.enqueue(object: Callback<MakitaProfile>{
            override fun onResponse(call: Call<MakitaProfile>?, response: Response<MakitaProfile>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)
                    401 -> callback.onUnauthorized(response.body()!!)
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaGetProfileError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<MakitaProfile>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaGetProfileError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun updateProfile(token: String, data: MakitaProfile, callback: IMakitaUpdateProfileCallBack){
        val authCall = MakitaAPI.instance.service!!.updateProfile(token, data)
        authCall.enqueue(object: Callback<MakitaProfile>{
            override fun onResponse(call: Call<MakitaProfile>?, response: Response<MakitaProfile>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)
                    401 -> callback.onUnauthorized(response.body()!!)
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MakitaUpdateProfileError " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<MakitaProfile>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("MakitaUpdateProfileError ", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    companion object {
        val instance: MakitaRemoteDataSource by lazy { MakitaRemoteDataSource() }
    }
}