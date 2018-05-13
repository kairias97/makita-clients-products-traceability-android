package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class Invoice(@SerializedName("id") val id: Int,
                   @SerializedName("fetesaStore") val purchaseStore: String,
                   @SerializedName("type") val purchaseType: String,
                   @SerializedName("physicalNumber") val physicalNumber: String,
                   @SerializedName("amount") val amount: Double,
                   @SerializedName("issuedDate") val issuedDate: String)