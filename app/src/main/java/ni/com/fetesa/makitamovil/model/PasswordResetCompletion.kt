package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 01/07/2018.
 */
data class PasswordResetCompletion(@SerializedName("username") val username: String, @SerializedName("confirmationCode") val confirmationCode: String,
                                   @SerializedName("requestID") val requestID: String, @SerializedName("newPassword") val newPassword: String)