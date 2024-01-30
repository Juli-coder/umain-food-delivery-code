package com.food.data.mapper

import com.app.data.base.Mapper
import com.food.data.network.entities.FilterDTO
import com.food.domain.entities.FilterInfo

class FilterMapper : Mapper<FilterDTO, FilterInfo> {
    override fun map(input: FilterDTO): FilterInfo {
        return FilterInfo(
            input.id.orEmpty(),
            input.name.orEmpty(),
            input.imageUrl.orEmpty()
        )
    }
}