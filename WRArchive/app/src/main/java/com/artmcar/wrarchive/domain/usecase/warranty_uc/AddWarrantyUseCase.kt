package com.artmcar.wrarchive.domain.usecase.warranty_uc

import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class AddWarrantyUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    suspend operator fun invoke(
        item: WarrantyModel
    ) {
        repository.insertWarranty(item)
    }
}