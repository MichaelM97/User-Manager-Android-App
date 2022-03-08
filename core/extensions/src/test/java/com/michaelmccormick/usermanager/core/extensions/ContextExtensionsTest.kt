package com.michaelmccormick.usermanager.core.extensions

import android.content.Context
import android.widget.Toast
import com.michaelmccormick.usermanager.core.test.MockExtension
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockExtension::class)
internal class ContextExtensionsTest {
    private val mockContext: Context = mockk()

    @Nested
    inner class ShowToast {
        @BeforeEach
        fun before() {
            mockkStatic(Toast::class)
        }

        @Test
        fun shouldShowToastUsingPassedValues() {
            // Given
            val stringResId = 1
            val length = Toast.LENGTH_LONG
            val mockToast: Toast = mockk()
            every { Toast.makeText(mockContext, stringResId, length) } returns mockToast
            every { mockToast.show() } returns mockk()

            // When
            mockContext.showToast(stringResId, length)

            // Then
            verify(exactly = 1) {
                Toast.makeText(mockContext, stringResId, length)
                mockToast.show()
            }
        }
    }
}
