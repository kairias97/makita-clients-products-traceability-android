package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 10/05/2018.
 */
interface IRegisterView {
    fun showRegistrationProcess()
    fun hideRegistrationProcess()
    fun showError()
    fun showCustomError(msg: String)
    fun navigateToValidateRegister()
    fun showEmptyFieldsError()
}