package com.michaelmccormick.usermanager.core.test

import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class MockExtension : AfterEachCallback, AfterAllCallback {
    override fun afterEach(context: ExtensionContext?) {
        clearAllMocks()
    }

    override fun afterAll(context: ExtensionContext?) {
        unmockkAll()
    }
}
