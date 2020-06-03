package com.example.laboratorio8.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HackerNewsUser (val created_at: String,
                           val title: String,
                           val author: String,
                           val points: String,
                           val objectID: String) : Parcelable

