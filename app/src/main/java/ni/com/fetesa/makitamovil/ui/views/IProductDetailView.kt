package ni.com.fetesa.makitamovil.ui.views

import ni.com.fetesa.makitamovil.model.Warranty

/**
 * Created by dusti on 15/05/2018.
 */
interface IProductDetailView {
    fun showSavingSerialProgress()
    fun hideSavingSerialProgress()
    fun showGettingWarrantyProgress()
    fun hideGettingWarrantyProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun showWarranty(warranty: Warranty)
    fun savingSerialSuccessful()
    fun navigateToLogin()
}