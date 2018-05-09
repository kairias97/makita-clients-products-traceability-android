package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 09/05/2018.
 */
data class LoginResponse(@SerializedName("token") val token: String, @SerializedName("message") val message: String)