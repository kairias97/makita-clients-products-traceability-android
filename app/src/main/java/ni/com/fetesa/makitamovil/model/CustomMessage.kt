package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class CustomMessage(@SerializedName("message") val message: String)