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
    @POST(URL_REGISTRATION_COMPLETION)
    fun completeRegistration(@Body body: RegistrationCompletionRequest): Call<RegistrationCompletionResponse>
    @GET(URL_PROFILE)
    fun getProfile(@Header("Authorization") token: String): Call<MakitaProfile>
    @PUT(URL_PROFILE)
    fun updateProfile(@Header("Authorization") token: String, @Body body: MakitaProfile): Call<MakitaProfile>
    @GET(URL_POINTS)
    fun getFidelizationPoints(@Header("Authorization") token: String): Call<UserFidelizationPoints>
    @GET(URL_PRODUCTS)
    fun getUserProducts(@Header("Authorization") token: String): Call<List<Product>>
    @GET(URL_INVOICES)
    fun getUserInvoices(@Header("Authorization") token: String): Call<List<Invoice>>
    @GET(URL_INVOICES_PRODUCTS)
    fun getInvoiceProducts(@Header("Authorization") token: String, @Path("invoiceID") invoiceID: Long) : Call<List<Product>>
    @POST(URL_INVOICES_BINDING)
    fun bindInvoice(@Header("Authorization") token: String, @Body invoiceBinding: InvoiceBindingRequest): Call<InvoiceBinding>
    @POST(URL_PRODUCTS_Warranty)
    fun saveWarranty(@Header("Authorization") token: String,  @Path("productID") productID: Long, @Body warrantyRequest: WarrantyBindingRequest): Call<Warranty>
    @GET(URL_PRODUCTS_Warranty)
    fun getProductWarranty(@Header("Authorization") token: String, @Path("productID") productID: Long): Call<Warranty>
    @PUT(URL_UPDATE_PASSWORD)
    fun updatePassword(@Header("Authorization") token: String, @Body body: PasswordUpdate): Call<PasswordUpdateResponse>
    @POST(URL_RESET_PASSWORD)
    fun resetPassword(@Body body: PasswordReset): Call<PasswordResetResponse>
    @POST(URL_RESET_PASSWORD_VERIFY_CODE)
    fun resetPasswordVerifyCode(@Body body: PasswordResetVerification): Call<PasswordResetVerificationResponse>
    @POST(URL_RESET_PASSWORD_COMPLETION)
    fun resetPasswordCompletion(@Body body: PasswordResetCompletion): Call<PasswordResetCompletionResponse>
    @GET(URL_ORDERS_QUOTED_GET)
    fun getQuotedOrders(@Header("Authorization") token: String): Call<List<OrderHeader>>
    @GET(URL_ORDERS_GET)
    fun getAllOrders(@Header("Authorization") token: String): Call<List<OrderHeader>>
    @POST(URL_ORDERS_ANSWER)
    fun answerOrder(@Header("Authorization") token: String, @Path("orderID") orderID: Int, @Body body: OrderQuoteAnswer): Call<OrderQuoteAnswerResponse>
    @GET(URL_RECOMMENDED_PRODUCTS_GET)
    fun getRecommendedProducts(@Header("Authorization") token: String): Call<List<RecommendedProduct>>

}