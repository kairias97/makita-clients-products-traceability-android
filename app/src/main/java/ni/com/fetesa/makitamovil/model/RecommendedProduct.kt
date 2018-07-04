package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 04/07/2018.
 */
data class RecommendedProduct(@SerializedName("name") val name: String, @SerializedName("description") val description: String,
                              @SerializedName("additionalInfo") val additionalInfo: String)