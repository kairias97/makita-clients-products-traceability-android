package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 10/05/2018.
 */
data class TokenMakita(@SerializedName("token") val token: String, @SerializedName("expirationDate") val expirationDate: String)