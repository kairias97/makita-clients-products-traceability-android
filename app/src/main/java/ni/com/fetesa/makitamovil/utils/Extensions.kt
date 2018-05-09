package ni.com.fetesa.makitamovil.utils

import android.content.Context
import android.util.Base64
import android.util.Base64.DEFAULT
import android.widget.Toast

/**
 * Created by kevin on 25/2/2018.
 */
fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), DEFAULT)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, length).show()
}

fun Context.toast(resID: Int, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, resID, length).show()
}