package com.umain.domain.entities

import java.io.Serializable

data class Restaurant(
    val deliveryTimeMinutes: Int = 0,
    val filterIds: List<String> = listOf(),
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val rating: Float = 0.0f
) : Serializable {
    var filterNames: List<String> = listOf()

    fun populateFilterNames() = filterNames.joinToString(separator = " • ")

}