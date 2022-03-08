package com.michaelmccormick.usermanager.core.uitest

import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.AfterClass
import org.junit.Rule

open class BaseUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun after() {
        clearAllMocks()
    }

    companion object {
        @JvmStatic
        @AfterClass
        fun afterClass() {
            unmockkAll()
        }
    }
}
