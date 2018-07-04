package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 03/07/2018.
 */
interface IOrderDetailView {
    fun navigateToReasonRejection()
    fun navigateToLogin()
    fun showAcceptingProgress()
    fun hideAcceptingProgress()
    fun showError()
    fun showCustomMessage(msg: String)
    fun navigateToMain()
}