package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 01/07/2018.
 */
interface IForgotPasswordView {
    fun showValidationProgress()
    fun hideValidationProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun showEmptyFields()
    fun navigateToVerificationCode()
    fun navigateToLogin()
}