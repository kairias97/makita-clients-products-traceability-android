package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaRegistrationCallBack
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.RegistrationResponse
import ni.com.fetesa.makitamovil.model.RegistrationsRequest
import ni.com.fetesa.makitamovil.presenter.IRegisterPresenter
import ni.com.fetesa.makitamovil.ui.views.IRegisterView

/**
 * Created by dusti on 10/05/2018.
 */
class RegisterPresenterImpl: IRegisterPresenter {

    private val mRegisterView: IRegisterView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource

    constructor(registerView: IRegisterView, makitaRemoteDataSource: MakitaRemoteDataSource){
        this.mRegisterView = registerView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
    }

    override fun register(documentTypeID: Int, identificationNumber: String) {
        mRegisterView.showRegistrationProcess()
        mMakitaRemoteDataSource.registerToMakita(RegistrationsRequest(documentTypeID, identificationNumber), object: IMakitaRegistrationCallBack{
            override fun onSuccess(response: RegistrationResponse) {
                if(response.isSuccessFull){
                    mRegisterView.hideRegistrationProcess()
                    mRegisterView.navigateToValidateRegister()
                }
                else{
                    mRegisterView.hideRegistrationProcess()
                    mRegisterView.showCustomError(response.message)
                }
            }

            override fun onFailure() {
                mRegisterView.hideRegistrationProcess()
                mRegisterView.showError()
            }
        })
    }
}