package ni.com.fetesa.makitamovil.data.remote.Callbacks

import ni.com.fetesa.makitamovil.model.LoginResponse

/**
 * Created by dusti on 09/05/2018.
 */
interface IMakitaValidateCredentialsCallBack {
    fun onSuccess(response: LoginResponse)
    fun onUnauthorized(response: LoginResponse)
    fun onFailure()
}