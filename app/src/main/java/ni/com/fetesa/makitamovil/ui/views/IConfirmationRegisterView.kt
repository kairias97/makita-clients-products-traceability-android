package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 10/05/2018.
 */
interface IConfirmationRegisterView {
    fun showValidatingCodeProcess()
    fun hideValidatingCodeProcess()
    fun showError()
    fun showCustomError(error: String)
    fun showEmptyFieldError()
    fun navigateToCompletionRegister(registrationID: String)
}