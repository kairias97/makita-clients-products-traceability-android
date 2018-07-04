package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 03/07/2018.
 */
interface IOrderAnswerReasonPresenter {
    fun rejectOrder(orderID: Int, reason: String)
}