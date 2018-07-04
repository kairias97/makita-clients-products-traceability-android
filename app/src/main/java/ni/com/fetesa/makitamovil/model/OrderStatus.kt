package ni.com.fetesa.makitamovil.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 02/07/2018.
 */
data class OrderStatus(@SerializedName("id") val ID: Int, @SerializedName("nafCode") val NAFCode: String,
                       @SerializedName("name") val name: String, @SerializedName("description") val description: String):Parcelable{

    constructor(parcel:Parcel):this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(ID)
        dest?.writeString(NAFCode)
        dest?.writeString(name)
        dest?.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<OrderStatus>{
        override fun createFromParcel(parcel: Parcel): OrderStatus {
            return OrderStatus(parcel)
        }

        override fun newArray(size: Int): Array<OrderStatus?> {
            return arrayOfNulls(size)
        }
    }
}