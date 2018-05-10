package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 10/05/2018.
 */
interface ICompletionRegisterPresenter {
    fun completeRegistration(documentTypeID: Int, identificationNumber: String, registrationID: String, username: String, password: String)
}