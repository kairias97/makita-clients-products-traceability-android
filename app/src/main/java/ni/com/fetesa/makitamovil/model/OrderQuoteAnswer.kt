package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/07/2018.
 */
data class OrderQuoteAnswer(@SerializedName("isAccepted") val isAccepted: Boolean, @SerializedName("reason") val reason: String)