package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaRegistrationVerificationCallBack
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.RegistrationVerificationRequest
import ni.com.fetesa.makitamovil.model.RegistrationVerificationResponse
import ni.com.fetesa.makitamovil.presenter.IConfirmationRegisterPresenter
import ni.com.fetesa.makitamovil.ui.views.IConfirmationRegisterView

/**
 * Created by dusti on 10/05/2018.
 */
class ConfirmationRegisterPresenterImpl: IConfirmationRegisterPresenter {
    private val mConfirmationRegisterView: IConfirmationRegisterView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource

    constructor(confirmationRegisterView: IConfirmationRegisterView, makitaRemoteDataSource: MakitaRemoteDataSource){
        this.mConfirmationRegisterView = confirmationRegisterView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
    }

    override fun verifyCode(code: String, typeID: Int, identificationNumber: String) {
        mConfirmationRegisterView.showValidatingCodeProcess()
        val codeInt = code.toInt()
        mMakitaRemoteDataSource.verifyRegistration(RegistrationVerificationRequest(typeID,identificationNumber,codeInt),object: IMakitaRegistrationVerificationCallBack{
            override fun onSuccess(response: RegistrationVerificationResponse) {
                if (response.isSuccessful){
                    mConfirmationRegisterView.hideValidatingCodeProcess()
                    mConfirmationRegisterView.navigateToCompletionRegister(response.registrationID)
                }
                else{
                    mConfirmationRegisterView.hideValidatingCodeProcess()
                    mConfirmationRegisterView.showCustomError(response.message)
                }
            }

            override fun onFailure() {
                mConfirmationRegisterView.hideValidatingCodeProcess()
                mConfirmationRegisterView.showError()
            }
        })
    }
}