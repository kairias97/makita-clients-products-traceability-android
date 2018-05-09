package ni.com.fetesa.makitamovil.data.api

import retrofit2.Call
import retrofit2.http.*
import ni.com.fetesa.makitamovil.utils.*
import ni.com.fetesa.makitamovil.model.*

/**
 * Created by dusti on 08/05/2018.
 */
interface IMakitaService {
    @POST(URL_LOGIN)
    fun validateMakitaCredentials(@Body body: LoginRequest): Call<LoginResponse>
    @POST(URL_REGISTRATION)
    fun registerToMakita(@Body body: RegistrationsRequest): Call<RegistrationResponse>
    @POST(URL_REGISTRATION_VERIFICATION)
    fun verifyRegistration(@Body body: RegistrationVerificationRequest): Call<RegistrationVerificationResponse>
}