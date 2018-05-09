package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 09/05/2018.
 */
data class RegistrationVerificationRequest(@SerializedName("documentTypeID") val documentTypeID: Int,
                                           @SerializedName("identificationNumber") val identificationNumber: String,
                                           @SerializedName("confimationCode") val confirmationCode: String)