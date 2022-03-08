package com.michaelmccormick.usermanager.network.factories

import com.michaelmccormick.usermanager.core.test.MockExtension
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExtendWith(MockExtension::class)
internal class ServiceFactoryTest {
    private interface FakeService

    private val baseUrl = "https://www.api.com/"
    private val mockOkHttpClientBuilder: OkHttpClient.Builder = mockk()
    private val mockOkHttpClient: OkHttpClient = mockk()
    private val mockRetrofitBuilder: Retrofit.Builder = mockk()
    private val mockGsonConverterFactory: GsonConverterFactory = mockk()
    private lateinit var serviceFactory: ServiceFactory

    @BeforeEach
    fun before() {
        every { mockOkHttpClientBuilder.readTimeout(any(), any()) } returns mockOkHttpClientBuilder
        every { mockOkHttpClientBuilder.build() } returns mockOkHttpClient
        every { mockRetrofitBuilder.baseUrl(any<String>()) } returns mockRetrofitBuilder
        every { mockRetrofitBuilder.client(any()) } returns mockRetrofitBuilder
        every { mockRetrofitBuilder.addConverterFactory(any()) } returns mockRetrofitBuilder
        serviceFactory = ServiceFactory(
            baseUrl = baseUrl,
            okHttpClientBuilder = mockOkHttpClientBuilder,
            retrofitBuilder = mockRetrofitBuilder,
            gsonConverterFactory = mockGsonConverterFactory,
        )
    }

    @Test
    fun shouldBuildService() {
        // Given
        val mockRetrofit: Retrofit = mockk()
        every { mockRetrofitBuilder.build() } returns mockRetrofit
        val mockBuiltService: FakeService = mockk()
        every { mockRetrofit.create(FakeService::class.java) } returns mockBuiltService

        // When
        val builtService = serviceFactory.build<FakeService>()

        // Then
        assertEquals(mockBuiltService, builtService)
        verify(exactly = 1) {
            mockOkHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            mockOkHttpClientBuilder.build()
            mockRetrofitBuilder.baseUrl(baseUrl)
            mockRetrofitBuilder.client(mockOkHttpClient)
            mockRetrofitBuilder.addConverterFactory(mockGsonConverterFactory)
            mockRetrofitBuilder.build()
            mockRetrofit.create(FakeService::class.java)
        }
    }
}
