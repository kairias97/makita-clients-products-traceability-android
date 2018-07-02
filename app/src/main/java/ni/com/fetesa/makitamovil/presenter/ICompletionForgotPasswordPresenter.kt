package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 01/07/2018.
 */
interface ICompletionForgotPasswordPresenter {
    fun resetPasswordCompletion(user: String, code: String, requestID: String, newPassword: String)
}