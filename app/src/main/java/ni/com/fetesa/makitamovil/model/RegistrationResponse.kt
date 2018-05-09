package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 09/05/2018.
 */
data class RegistrationResponse(@SerializedName("isSuccessful") val isSuccessFull: Boolean,
                                @SerializedName("message") val message: String)