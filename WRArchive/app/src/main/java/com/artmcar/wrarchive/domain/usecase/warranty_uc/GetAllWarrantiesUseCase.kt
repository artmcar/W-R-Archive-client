package com.artmcar.wrarchive.domain.usecase.warranty_uc

import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class GetAllWarrantiesUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    operator fun invoke() = repository.getAllWarranties()
}