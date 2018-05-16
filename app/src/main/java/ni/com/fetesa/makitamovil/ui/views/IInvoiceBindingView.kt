package ni.com.fetesa.makitamovil.ui.views

import ni.com.fetesa.makitamovil.model.Product

/**
 * Created by dusti on 14/05/2018.
 */
interface IInvoiceBindingView {
    fun showBindingInvoiceProgress()
    fun hideBindingInvoiceProgress()
    fun showGettingProductsProgress()
    fun hideGettingProductsProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun setProductsList(data: MutableList<Product>)
    fun navigateToLogin()
}