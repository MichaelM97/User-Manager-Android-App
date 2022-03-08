package com.michaelmccormick.usermanager.core.uitest

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert

fun SemanticsNodeInteraction.assertIsError(): SemanticsNodeInteraction =
    assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Error))

fun SemanticsNodeInteraction.assertIsNotError(): SemanticsNodeInteraction =
    assert(!SemanticsMatcher.keyIsDefined(SemanticsProperties.Error))
