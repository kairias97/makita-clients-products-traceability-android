package ni.com.fetesa.makitamovil.utils

import com.google.gson.Gson

/**
 * Created by kevin on 13/5/2018.
 */
class GsonParser {
    companion object {
        fun <T> parseJson(source: String, destinationClass: Class<T>): T {
            var gson = Gson()
            return gson.fromJson(source, destinationClass)
        }
    }
}