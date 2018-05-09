package ni.com.fetesa.makitamovil.data.remote.Callbacks

import ni.com.fetesa.makitamovil.model.RegistrationResponse

/**
 * Created by dusti on 09/05/2018.
 */
interface IMakitaRegistrationCallBack {
    fun onSuccess(response: RegistrationResponse)
    fun onFailure()
}