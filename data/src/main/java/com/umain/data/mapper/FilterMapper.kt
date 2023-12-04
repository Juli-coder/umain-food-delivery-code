package com.umain.data.mapper

import com.app.data.base.Mapper
import com.umain.data.network.entities.FilterDTO
import com.umain.domain.entities.FilterInfo

class FilterMapper : Mapper<FilterDTO, FilterInfo> {
    override fun map(input: FilterDTO): FilterInfo {
        return FilterInfo(
            input.id.orEmpty(),
            input.name.orEmpty(),
            input.imageUrl.orEmpty()
        )
    }
}