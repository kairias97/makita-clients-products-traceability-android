package ni.com.fetesa.makitamovil.ui.fragmentViews

import ni.com.fetesa.makitamovil.model.OrderHeader

/**
 * Created by dusti on 02/07/2018.
 */
interface IWorkshopFragmentView {
    fun showLoadingOrdersProgress()
    fun hideLoadingOrdersProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun navigateToOrderHeader(order: OrderHeader)
    fun navigateToLogin()
    fun setOrdersList(data: List<OrderHeader>)
}