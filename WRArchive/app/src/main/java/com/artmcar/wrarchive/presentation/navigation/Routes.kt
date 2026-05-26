package com.artmcar.wrarchive.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object WarrantyRoute

@Serializable
object ReceiptRoute

@Serializable
object ProfileRoute

@Serializable
object AuthGraph

@Serializable
object MainGraph

@Serializable
data class AddEditWarrantyRoute(
    val warrantyId: Int? = null
)
@Serializable
data class AddEditReceiptRoute(
    val receiptId: Int? = null
)