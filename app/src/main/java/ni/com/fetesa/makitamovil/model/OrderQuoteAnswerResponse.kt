package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/07/2018.
 */
data class OrderQuoteAnswerResponse(@SerializedName("IsSuccessful") val isSuccessful: Boolean, @SerializedName("Message") val Message: String)