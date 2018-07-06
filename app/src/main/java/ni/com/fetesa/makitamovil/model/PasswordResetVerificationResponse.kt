package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 01/07/2018.
 */
data class PasswordResetVerificationResponse(@SerializedName("isValid") val isValid: Boolean, @SerializedName("message") val message: String,
                                             @SerializedName("requestID") val requestID: String)