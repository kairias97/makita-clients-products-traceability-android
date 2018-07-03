package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 02/07/2018.
 */
interface IWorkshopFragmentPresenter {
    var mIsQuoted:Boolean
    fun getQuotedOrders()
    fun getAllOrders()
}