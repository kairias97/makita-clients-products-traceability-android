package ni.com.fetesa.makitamovil.data.remote

import android.support.compat.BuildConfig
import android.util.Log
import com.google.gson.JsonParser
import ni.com.fetesa.makitamovil.data.api.MakitaAPI
import ni.com.fetesa.makitamovil.model.*
import ni.com.fetesa.makitamovil.data.remote.Callbacks.*
import ni.com.fetesa.makitamovil.utils.GsonParser
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

    fun getFidelizationPoints(token: String, callback: IMakitaResponseCallback<UserFidelizationPoints>){
        val authCall = MakitaAPI.instance.service!!.getFidelizationPoints(token)
        authCall.enqueue(object:Callback<UserFidelizationPoints> {
            override fun onFailure(call: Call<UserFidelizationPoints>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<UserFidelizationPoints>?, response: Response<UserFidelizationPoints>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }

    fun getProductsByUser(token: String, callback: IMakitaResponseCallback<List<Product>>) {
        val authCall = MakitaAPI.instance.service!!.getUserProducts(token)
        authCall.enqueue(object:Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<List<Product>>?, response: Response<List<Product>>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }

    fun getProductsByInvoice(token: String, invoiceID: Long, callback: IMakitaResponseCallback<List<Product>>) {
        val authCall = MakitaAPI.instance.service!!.getInvoiceProducts(token, invoiceID)
        authCall.enqueue(object:Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<List<Product>>?, response: Response<List<Product>>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }
    fun getInvoicesByUser(token: String, callback: IMakitaResponseCallback<List<Invoice>>){
        val authCall = MakitaAPI.instance.service!!.getUserInvoices(token)
        authCall.enqueue(object:Callback<List<Invoice>> {
            override fun onFailure(call: Call<List<Invoice>>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<List<Invoice>>?, response: Response<List<Invoice>>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }
    fun getProductWarranty(token: String, productID: Long, callback: IMakitaResponseCallback<Warranty>) {
        val authCall = MakitaAPI.instance.service!!.getProductWarranty(token, productID)
        authCall.enqueue(object:Callback<Warranty> {
            override fun onFailure(call: Call<Warranty>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<Warranty>?, response: Response<Warranty>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }

    fun saveProductWarranty(token: String, productID: Long, body: WarrantyBindingRequest, callback: IMakitaResponseCallback<Warranty>) {
        val authCall = MakitaAPI.instance.service!!.saveWarranty(token, productID, body)
        authCall.enqueue(object:Callback<Warranty> {
            override fun onFailure(call: Call<Warranty>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<Warranty>?, response: Response<Warranty>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }
    fun bindUserInvoice(token: String, body: InvoiceBindingRequest, callback: IMakitaResponseCallback<InvoiceBinding>) {
        val authCall = MakitaAPI.instance.service!!.bindInvoice(token, body)
        authCall.enqueue(object:Callback<InvoiceBinding> {
            override fun onFailure(call: Call<InvoiceBinding>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<InvoiceBinding>?, response: Response<InvoiceBinding>?) {
                when(response!!.code()) {
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }

        })
    }
    fun updatePassword(token: String, body: PasswordUpdate, callback: IMakitaResponseCallback<PasswordUpdateResponse>){
        val authCall = MakitaAPI.instance.service!!.updatePassword(token, body)
        authCall.enqueue(object: Callback<PasswordUpdateResponse>{
            override fun onFailure(call: Call<PasswordUpdateResponse>?, t: Throwable?) {
                callback.onNetworkFailure()
            }

            override fun onResponse(call: Call<PasswordUpdateResponse>?, response: Response<PasswordUpdateResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onSessionExpired(customMessage.message)
                    }
                    else -> {
                        var customMessage = GsonParser.parseJson(response!!.errorBody()!!.string(),
                                CustomMessage::class.java)
                        callback.onCustomMessage(customMessage.message)
                    }
                }
            }
        })
    }

    companion object {
        val instance: MakitaRemoteDataSource by lazy { MakitaRemoteDataSource() }
    }
}