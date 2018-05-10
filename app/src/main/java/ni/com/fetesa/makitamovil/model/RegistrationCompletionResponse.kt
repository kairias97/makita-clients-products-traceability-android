package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 10/05/2018.
 */
data class RegistrationCompletionResponse(@SerializedName("isSuccessful") val isSuccessful: Boolean,
                                          @SerializedName("message") val message: String,
                                          @SerializedName("token") val token: TokenMakita)