package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 10/05/2018.
 */
interface IConfirmationRegisterPresenter {
    fun verifyCode(code: String, typeID: Int, identificationNumber: String)
}