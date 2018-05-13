package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class UserFidelizationPoints(@SerializedName("total") val total: Double,
                                  @SerializedName("expirationDate") val expirationDate: String)