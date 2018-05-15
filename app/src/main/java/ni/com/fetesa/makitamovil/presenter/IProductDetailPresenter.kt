package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 15/05/2018.
 */
interface IProductDetailPresenter {
    fun saveSerial(serial: String, productID: Int)
    fun getWarranty(productID: Int)
}