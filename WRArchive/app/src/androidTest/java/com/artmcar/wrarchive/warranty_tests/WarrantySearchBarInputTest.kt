package com.artmcar.wrarchive.warranty_tests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artmcar.wrarchive.presentation.warranty.WarrantySearchBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WarrantySearchBarInputTest {
    @get:Rule
    val composeRule = createComposeRule()
    @Test
    fun search_input_changes_text() {
        composeRule.setContent {
            var query by remember { mutableStateOf("") }
            WarrantySearchBar(
                query = query,
                onQueryChange = {
                    query = it
                }
            )
        }
        composeRule
            .onNode(hasSetTextAction())
            .performTextInput("Laptop")
        composeRule
            .onNodeWithText("Laptop")
            .assertExists()
    }
}