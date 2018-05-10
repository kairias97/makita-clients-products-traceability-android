package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 10/05/2018.
 */
data class RegistrationCompletionRequest(@SerializedName("documentTypeID") val documentTypeID: Int,
                                         @SerializedName("identificationNumber") val identificationNumber: String,
                                         @SerializedName("registrationID") val registrationID: String,
                                         @SerializedName("username") val username: String,
                                         @SerializedName("password") val password: String)