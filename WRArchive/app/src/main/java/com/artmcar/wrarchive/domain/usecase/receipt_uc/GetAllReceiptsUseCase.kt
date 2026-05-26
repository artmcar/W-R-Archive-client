package com.artmcar.wrarchive.domain.usecase.receipt_uc

import com.artmcar.wrarchive.domain.repository.ReceiptRepository
import javax.inject.Inject

class GetAllReceiptsUseCase @Inject constructor(
    private val repository: ReceiptRepository
) {
    operator fun invoke() = repository.getAllReceipts()
}