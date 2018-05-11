package ni.com.fetesa.makitamovil.data.remote.Callbacks

import ni.com.fetesa.makitamovil.model.MakitaProfile

/**
 * Created by dusti on 11/05/2018.
 */
interface IMakitaGetProfileCallBack {
    fun onSuccess(response: MakitaProfile)
    fun onFailure()
    fun onUnauthorized(response: MakitaProfile)
}