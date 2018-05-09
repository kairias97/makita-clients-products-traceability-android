package ni.com.fetesa.makitamovil.data.remote.Callbacks

import ni.com.fetesa.makitamovil.model.RegistrationVerificationResponse

/**
 * Created by dusti on 09/05/2018.
 */
interface IMakitaRegistrationVerificationCallBack {
    fun onSuccess(response: RegistrationVerificationResponse)
    fun onFailure()
}