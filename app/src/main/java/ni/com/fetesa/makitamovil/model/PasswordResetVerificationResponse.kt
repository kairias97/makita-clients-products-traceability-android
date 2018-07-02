package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 01/07/2018.
 */
data class PasswordResetVerificationResponse(@SerializedName("IsValid") val isValid: Boolean, @SerializedName("Message") val message: String,
                                             @SerializedName("RequestID") val requestID: String)