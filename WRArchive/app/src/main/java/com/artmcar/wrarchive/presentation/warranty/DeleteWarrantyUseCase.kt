package com.artmcar.wrarchive.presentation.warranty

import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class DeleteWarrantyUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    suspend operator fun invoke(
        item: WarrantyModel
    ) {
        repository.deleteWarranty(item)
    }
}