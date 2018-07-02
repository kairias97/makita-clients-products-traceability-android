package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 01/07/2018.
 */
interface IVerificationCodeForgotPasswordPresenter {
    fun validateCode(user: String, code: String)
}