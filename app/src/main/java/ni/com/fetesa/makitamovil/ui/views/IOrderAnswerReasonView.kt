package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 03/07/2018.
 */
interface IOrderAnswerReasonView {
    fun showRejectingOrderProcess()
    fun hideRejectingOrderProcess()
    fun navigateToLogin()
    fun navigateToMain()
    fun showError()
    fun showCustomMessage(msg: String)
    fun showEmptyFields()
}