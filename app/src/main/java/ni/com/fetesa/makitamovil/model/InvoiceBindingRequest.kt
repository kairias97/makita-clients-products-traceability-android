package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class InvoiceBindingRequest(@SerializedName("invoiceNumber") val invoiceNumber: String)