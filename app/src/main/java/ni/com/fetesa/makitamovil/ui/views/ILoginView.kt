package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 09/05/2018.
 */
interface ILoginView {
    fun showValidatingCredentials()
    fun showCustomError(error: String)
    fun showError()
    fun hideValidatingCredentials()
    fun prepareToNavigateToMain()
}