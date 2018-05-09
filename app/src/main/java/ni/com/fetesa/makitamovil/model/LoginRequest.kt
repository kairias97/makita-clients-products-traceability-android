package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 09/05/2018.
 */
data class LoginRequest(@SerializedName("username") val username: String, @SerializedName("password") val password: String)