package ni.com.fetesa.makitamovil.ui.views

import ni.com.fetesa.makitamovil.model.RecommendedProduct

/**
 * Created by dusti on 04/07/2018.
 */
interface IRecommendedProductView {
    fun showLoadingRecommendedProducts()
    fun hideLoadingRecommendedProducts()
    fun showError()
    fun showCustomMessage(msg: String)
    fun navigateToLogin()
    fun setRecommendationsList(data: MutableList<RecommendedProduct>)
}