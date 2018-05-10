package ni.com.fetesa.makitamovil.data.remote.Callbacks

import ni.com.fetesa.makitamovil.model.RegistrationCompletionResponse

/**
 * Created by dusti on 10/05/2018.
 */
interface IMakitaRegistrationCompletionCallBack {
    fun onSuccess(response: RegistrationCompletionResponse)
    fun onFailure()
}