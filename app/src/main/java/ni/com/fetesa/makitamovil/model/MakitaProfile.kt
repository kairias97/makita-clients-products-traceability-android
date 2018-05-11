package ni.com.fetesa.makitamovil.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 11/05/2018.
 */
data class MakitaProfile(@SerializedName("username") val username: String, @SerializedName("fetesaAlias") val fetesaAlias: String,
                         @SerializedName("clientFetesaCode") val clientFetesaCode: Int, @SerializedName("documentTypeID") val documentTypeID: Int,
                         @SerializedName("identificationNumber") val identificationNumber: String, @SerializedName("firstName") val firstName: String,
                         @SerializedName("middleName") val middleName: String, @SerializedName("lastName") val lastName: String,
                         @SerializedName("secondLastName") val secondLastName: String, @SerializedName("birthDate") val birthDate: String?,
                         @SerializedName("isMale") val isMale: Boolean?, @SerializedName("primaryEmail") val primaryEmail: String,
                         @SerializedName("secondaryEmail") val secondaryEmail: String, @SerializedName("cellPhone") val cellPhone: String,
                         @SerializedName("message") val message: String)