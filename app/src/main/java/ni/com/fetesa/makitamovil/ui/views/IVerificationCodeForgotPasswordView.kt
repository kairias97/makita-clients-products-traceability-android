package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 01/07/2018.
 */
interface IVerificationCodeForgotPasswordView {
    fun showVerificationProgress()
    fun hideVerificationProgress()
    fun showError()
    fun showCustomMessage(msg: String)
    fun navigateToLogin()
    fun navigateToResetPasswordCompletion(requestID: String)
    fun showEmptyFields()
}