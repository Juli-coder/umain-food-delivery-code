package com.food.domain.entities

data class FilterInfo(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = ""
) {
    var isSelected: Boolean = false
}
