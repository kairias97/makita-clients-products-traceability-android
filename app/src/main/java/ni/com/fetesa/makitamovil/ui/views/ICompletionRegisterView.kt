package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 10/05/2018.
 */
interface ICompletionRegisterView {
    fun showCompletionRegisterProcess()
    fun hideCompletionRegisterProcess()
    fun showError()
    fun showCustomError(error: String)
    fun navigateToMain()
    fun showEmptyFieldError()
    fun showPasswordsDontMatchError()
}