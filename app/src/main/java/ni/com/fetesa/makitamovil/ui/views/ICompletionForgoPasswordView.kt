package ni.com.fetesa.makitamovil.ui.views

/**
 * Created by dusti on 01/07/2018.
 */
interface ICompletionForgoPasswordView {
    fun showSavingPasswordProgress()
    fun hideSavingkPasswordProgress()
    fun showError()
    fun showCustomMessage(msg: String)
    fun navigateToLogin()
    fun showEmptyFields()
}