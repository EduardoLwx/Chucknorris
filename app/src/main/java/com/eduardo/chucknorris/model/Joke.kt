package com.eduardo.chucknorris.model

import android.os.Parcel
import android.os.Parcelable

data class Joke(val id: String, val categories: List<String>, val iconUrl: String, val value: String,
                       val url: String) : Parcelable {

    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        arrayOf<String>().apply {
            parcel.readStringArray(this)
        }.toList(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(id)
            writeStringArray(categories.toTypedArray())
            writeString(iconUrl)
            writeString(value)
            writeString(url)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Joke> {
        override fun createFromParcel(source: Parcel): Joke {
            return Joke(source)
        }

        override fun newArray(size: Int): Array<Joke?> {
            return arrayOfNulls(size)
        }
    }
}