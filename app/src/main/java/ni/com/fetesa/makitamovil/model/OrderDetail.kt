package ni.com.fetesa.makitamovil.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 02/07/2018.
 */
data class OrderDetail(@SerializedName("id") val ID: Int,
                       @SerializedName("repairOrderID") val repairOrderID: Int,
                       @SerializedName("lineNumber") val lineNumber: Int,
                       @SerializedName("partNumber") val partNumber: String?,
                       @SerializedName("partDescription") val partDescription: String?,
                       @SerializedName("articleNumber") val articleNumber: String?,
                       @SerializedName("quantity") val quantity: Int,
                       @SerializedName("unitPrice") val unitPrice: Double,
                       @SerializedName("warehouseCode") val warehouseCode: Int,
                       @SerializedName("warehouse") val warehouse: String?):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeInt(repairOrderID)
        parcel.writeInt(lineNumber)
        parcel.writeString(partNumber)
        parcel.writeString(partDescription)
        parcel.writeString(articleNumber)
        parcel.writeInt(quantity)
        parcel.writeDouble(unitPrice)
        parcel.writeInt(warehouseCode)
        parcel.writeString(warehouse)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetail> {
        override fun createFromParcel(parcel: Parcel): OrderDetail {
            return OrderDetail(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetail?> {
            return arrayOfNulls(size)
        }
    }

}