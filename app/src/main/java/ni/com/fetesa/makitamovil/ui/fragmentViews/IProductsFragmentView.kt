package ni.com.fetesa.makitamovil.ui.fragmentViews

import ni.com.fetesa.makitamovil.model.Product

/**
 * Created by dusti on 14/05/2018.
 */
interface IProductsFragmentView {
    fun showLoadingProducts()
    fun hideLoadingProducts()
    fun showCustomMessage(msg: String)
    fun showError()
    fun setProductList(data: MutableList<Product>)
    fun navigateToLogin()
    fun navigateToRecommendedProducts()
}