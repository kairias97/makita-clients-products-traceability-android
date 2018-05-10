package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaRegistrationCompletionCallBack
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.RegistrationCompletionRequest
import ni.com.fetesa.makitamovil.model.RegistrationCompletionResponse
import ni.com.fetesa.makitamovil.presenter.ICompletionRegisterPresenter
import ni.com.fetesa.makitamovil.ui.views.ICompletionRegisterView

/**
 * Created by dusti on 10/05/2018.
 */
class CompletionRegisterPresenterImpl: ICompletionRegisterPresenter {

    private val mCompletionRegisterView: ICompletionRegisterView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(completionRegisterView: ICompletionRegisterView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mCompletionRegisterView = completionRegisterView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun completeRegistration(documentTypeID: Int, identificationNumber: String, registrationID: String, username: String, password: String) {
        mCompletionRegisterView.showCompletionRegisterProcess()
        mMakitaRemoteDataSource.completeRegistration(RegistrationCompletionRequest(documentTypeID, identificationNumber, registrationID, username, password),
                object: IMakitaRegistrationCompletionCallBack{
                    override fun onSuccess(response: RegistrationCompletionResponse) {
                        if(response.isSuccessful){
                            val authToken = response.token.token
                            mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN,
                                    authToken)
                            mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.EMAIL,
                                    authToken)

                            MakitaUserSession.instance.authToken = authToken
                            MakitaUserSession.instance.email = username

                            mCompletionRegisterView.hideCompletionRegisterProcess()
                            mCompletionRegisterView.showCustomError(response.message)
                            mCompletionRegisterView.navigateToMain()
                        }
                        else{
                            mCompletionRegisterView.hideCompletionRegisterProcess()
                            mCompletionRegisterView.showCustomError(response.message)
                        }
                    }

                    override fun onFailure() {
                        mCompletionRegisterView.hideCompletionRegisterProcess()
                        mCompletionRegisterView.showError()
                    }
                })
    }
}