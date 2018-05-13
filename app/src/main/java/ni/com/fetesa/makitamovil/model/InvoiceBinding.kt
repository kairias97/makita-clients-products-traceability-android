package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class InvoiceBinding(@SerializedName("isSuccessful") val isSuccessful: Boolean,
                          @SerializedName("message") val message: String,
                          @SerializedName("invoiceID") val invoiceID: Long)