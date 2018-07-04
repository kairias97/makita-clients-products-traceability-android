package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/07/2018.
 */
data class OrderQuoteAnswerResponse(@SerializedName("isSuccessful") val isSuccessful: Boolean, @SerializedName("message") val Message: String)