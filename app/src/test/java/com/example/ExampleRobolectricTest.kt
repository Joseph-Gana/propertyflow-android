package com.example

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.core.app.ApplicationProvider
import com.example.ui.screens.AboutScreen
import com.example.ui.theme.PropertyFlowTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("PropertyFlow", appName)
  }

  @Test
  fun `verify about screen content and developer attribution`() {
    composeTestRule.setContent {
      PropertyFlowTheme {
        AboutScreen(onNavigateBack = {})
      }
    }

    composeTestRule.onNodeWithText("Joseph Gana").assertIsDisplayed()
    composeTestRule.onNodeWithText("Web & Software Developer").assertIsDisplayed()
    composeTestRule.onNodeWithText("Version 1.0.0").assertIsDisplayed()
    composeTestRule.onNodeWithText("Prototype").assertIsDisplayed()
    composeTestRule.onNodeWithText("Smart Property Management, Simplified.").assertIsDisplayed()
  }
}
