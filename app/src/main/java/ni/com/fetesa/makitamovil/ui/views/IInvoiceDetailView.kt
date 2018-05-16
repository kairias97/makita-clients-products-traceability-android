package ni.com.fetesa.makitamovil.ui.views

import ni.com.fetesa.makitamovil.model.Product

/**
 * Created by dusti on 16/05/2018.
 */
interface IInvoiceDetailView {
    fun showLoadingProductsProgress()
    fun hideLoadingProductsProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun setProductsList(data: MutableList<Product>)
    fun navigateToProductDetail(product: Product)
    fun navigateToLogin()
}