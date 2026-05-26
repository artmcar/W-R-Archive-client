package com.artmcar.wrarchive.presentation.warranty

import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import javax.inject.Inject

class GetAllWarrantiesUseCase @Inject constructor(
    private val repository: WarrantyRepository
) {
    operator fun invoke() = repository.getAllWarranties()
}