package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class Product(@SerializedName("id") val id: Int,
                   @SerializedName("description") val description: String,
                   @SerializedName("price") val purchasePrice: Double,
                   @SerializedName("measureUnit") val measureUnit: String,
                   @SerializedName("partNumber") val partNumber: String,
                   @SerializedName("serialNumber") val serialNumber: String,
                   @SerializedName("fetesaStore") val purchaseStore: String,
                   @SerializedName("type") val purchaseType: String,
                   @SerializedName("physicalInvoiceNumber") val physicalInvoice: String,
                   @SerializedName("purchaseDate") val purchaseDate: String,
                   @SerializedName("kgWeight") val kgWeight: String
                   )