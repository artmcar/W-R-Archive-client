package com.artmcar.wrarchive.domain.usecase.warranty_uc

import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class UpdateWarrantyUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    suspend operator fun invoke(item: WarrantyModel) {
        repository.updateWarranty(item)
    }
}