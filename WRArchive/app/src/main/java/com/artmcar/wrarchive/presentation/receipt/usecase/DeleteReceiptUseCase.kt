package com.artmcar.wrarchive.presentation.receipt.usecase

import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.domain.repository.ReceiptRepository
import javax.inject.Inject

class DeleteReceiptUseCase @Inject constructor(
    private val repository: ReceiptRepository
) {
    suspend operator fun invoke(
        item: ReceiptModel
    ) {
        repository.deleteReceipt(item)
    }
}