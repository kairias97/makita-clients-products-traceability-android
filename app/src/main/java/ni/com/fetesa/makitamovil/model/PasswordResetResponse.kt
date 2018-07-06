package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 01/07/2018.
 */
data class PasswordResetResponse(@SerializedName("isSuccessful") val isSuccesful: Boolean, @SerializedName("message") val message: String)