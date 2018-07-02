package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 01/07/2018.
 */
data class PasswordResetVerification(@SerializedName("username") val username: String, @SerializedName("confirmationCode") val confirmationCode: String)