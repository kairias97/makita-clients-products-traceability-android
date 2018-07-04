package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaValidateCredentialsCallBack
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.LoginRequest
import ni.com.fetesa.makitamovil.model.LoginResponse
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.presenter.ILoginPresenter
import ni.com.fetesa.makitamovil.ui.views.ILoginView

/**
 * Created by dusti on 09/05/2018.
 */
class LoginPresenterImpl: ILoginPresenter {
    private val mLoginView: ILoginView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager, loginView: ILoginView, makitaRemoteDataSource: MakitaRemoteDataSource){
        this.mLoginView = loginView
        this.mSharedPrefManager = sharedPrefManager
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
    }

    override fun validateCredentials(username: String, password: String) {
        mLoginView.showValidatingCredentials()
        mMakitaRemoteDataSource.validateMakitaCredentials(LoginRequest(username, password), object : IMakitaResponseCallback<LoginResponse> {
            override fun onSuccess(response: LoginResponse) {
                if (response.token != "") {
                    val authToken = response.token
                    mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN,
                            authToken)
                    mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.EMAIL,
                            authToken)

                    MakitaUserSession.instance.authToken = authToken
                    MakitaUserSession.instance.email = username

                    mLoginView.hideValidatingCredentials()
                    mLoginView.prepareToNavigateToMain()
                }
            }

            override fun onNetworkFailure() {
                mLoginView.hideValidatingCredentials()
                mLoginView.showError()
            }

            override fun onCustomMessage(message: String) {
                mLoginView.hideValidatingCredentials()
                mLoginView.showCustomError(message)
            }

            override fun onSessionExpired(message: String) {
                mLoginView.hideValidatingCredentials()
                mLoginView.showCustomError(message)
            }
        })
    }
}