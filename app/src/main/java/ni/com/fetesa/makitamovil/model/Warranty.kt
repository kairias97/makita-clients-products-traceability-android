package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class Warranty(@SerializedName("productWarrantyID") val id: Int,
                    @SerializedName("startDate") val startDate: String,
                    @SerializedName("endDate") val endDate: String,
                    @SerializedName("serialNumber") val serialNumber: String,
                    @SerializedName("purchaseDate") val purchaseDate: String,
                    @SerializedName("partNumber") val productPartNumber: String,
                    @SerializedName("fetesaStore") val originStore: String,
                    @SerializedName("physicalInvoiceNumber") val physicalInvoice: String,
                    @SerializedName("active") val active: Boolean,
                    @SerializedName("invalidationReason") val invalidationReason: String)