package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 01/07/2018.
 */
interface IPasswordUpdateView {
    fun showUpdatingPasswordProgress()
    fun hideUpdatingPasswordProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun navigateToLogin()
    fun navigateToMain()
}