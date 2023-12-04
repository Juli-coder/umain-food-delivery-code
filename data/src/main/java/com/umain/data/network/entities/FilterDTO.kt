package com.umain.data.network.entities

import com.google.gson.annotations.SerializedName

data class FilterDTO(

    val id: String?,
    val name: String?,

    @SerializedName("image_url")
    val imageUrl: String?
)
