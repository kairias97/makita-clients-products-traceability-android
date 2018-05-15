package ni.com.fetesa.makitamovil.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 13/5/2018.
 */
data class Product(@SerializedName("id") val id: Int,
                   @SerializedName("description") val description: String,
                   @SerializedName("price") val purchasePrice: Double,
                   @SerializedName("measureUnit") val measureUnit: String,
                   @SerializedName("partNumber") val partNumber: String,
                   @SerializedName("serialNumber") val serialNumber: String?,
                   @SerializedName("fetesaStore") val purchaseStore: String,
                   @SerializedName("type") val purchaseType: String,
                   @SerializedName("physicalInvoiceNumber") val physicalInvoice: String,
                   @SerializedName("purchaseDate") val purchaseDate: String,
                   @SerializedName("kgWeight") val kgWeight: String
                   ):Parcelable {
    constructor(parcel: Parcel):this(parcel.readInt(), parcel.readString(), parcel.readDouble(),
            parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString() )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(description)
        dest?.writeDouble(purchasePrice)
        dest?.writeString(measureUnit)
        dest?.writeString(partNumber)
        dest?.writeString(serialNumber)
        dest?.writeString(purchaseStore)
        dest?.writeString(purchaseType)
        dest?.writeString(physicalInvoice)
        dest?.writeString(purchaseDate)
        dest?.writeString(kgWeight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}