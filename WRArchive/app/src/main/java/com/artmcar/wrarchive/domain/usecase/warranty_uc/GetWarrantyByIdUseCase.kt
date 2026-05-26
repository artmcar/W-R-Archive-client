package com.artmcar.wrarchive.domain.usecase.warranty_uc

import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class GetWarrantyByIdUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    suspend operator fun invoke(id: Int) = repository.getWarrantyById(id)
}