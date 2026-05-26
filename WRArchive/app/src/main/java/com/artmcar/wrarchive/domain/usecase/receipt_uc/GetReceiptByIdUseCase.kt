package com.artmcar.wrarchive.domain.usecase.receipt_uc

import com.artmcar.wrarchive.domain.repository.ReceiptRepository
import javax.inject.Inject

class GetReceiptByIdUseCase @Inject constructor(
    private val repository: ReceiptRepository
) {
    suspend operator fun invoke(id: Int) = repository.getReceiptById(id)
}