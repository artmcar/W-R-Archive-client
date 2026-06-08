package com.artmcar.wrarchive.warranty_tests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.presentation.warranty.WarrantySearchBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WarrantySearchBarTest {
    @get:Rule
    val composeRule = createComposeRule()
    @Test
    fun search_bar_is_displayed() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val text = context.getString(R.string.search_hint)
        composeRule.setContent {
            WarrantySearchBar(
                query = "",
                onQueryChange = {}
            )
        }
        composeRule
            .onNodeWithText(text)
            .assertExists()
    }
}