package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 01/07/2018.
 */
interface IMainView {
    fun prepareToNavigateToPasswordUpdate()
    fun confirmLogout()
    fun executeLogout()
    fun navigateToLogin()
    fun showLoggingOutProgress()
    fun hideLoggingOutProgress()
}