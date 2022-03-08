package com.michaelmccormick.usermanager.repositories.models

import com.google.gson.Gson
import com.michaelmccormick.usermanager.core.test.MockExtension
import io.mockk.every
import io.mockk.mockk
import java.io.Reader
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException

@ExtendWith(MockExtension::class)
internal class GoRestErrorTest {
    private val mockGson: Gson = mockk()
    private val mockHttpException: HttpException = mockk()
    private val mockReader: Reader = mockk()

    @BeforeEach
    fun before() {
        every { mockHttpException.response()?.errorBody()?.charStream() } returns mockReader
    }

    @Test
    fun shouldUseGsonToParseGoRestErrorBody() {
        // Given
        val errorArray = arrayOf(
            GoRestError(field = "field1", message = "message1"),
            GoRestError(field = "field2", message = "message2"),
        )
        every { mockGson.fromJson(mockReader, Array<GoRestError>::class.java) } returns errorArray

        // When
        val returnedValue = mockHttpException.parseGoRestErrorBody(mockGson)

        // Then
        assertEquals(errorArray, returnedValue)
    }

    @Test
    fun shouldReturnNullWhenParseFails() {
        // Given
        every { mockGson.fromJson(mockReader, Array<GoRestError>::class.java) } throws Exception()

        // When
        val returnedValue = mockHttpException.parseGoRestErrorBody(mockGson)

        // Then
        assertEquals(null, returnedValue)
    }
}
