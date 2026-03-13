package com.yage.opencode_client

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.yage.opencode_client.data.model.Session
import com.yage.opencode_client.ui.session.SessionList
import org.junit.Rule
import org.junit.Test

class SessionListInstrumentedTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun sessionListCanScrollToLaterSessionsWithoutClientPaging() {
        val sessions = (1..40).map { index ->
            Session(
                id = "session-$index",
                directory = "/tmp/project-$index",
                title = "Session $index"
            )
        }

        composeRule.setContent {
            MaterialTheme {
                SessionList(
                    sessions = sessions,
                    currentSessionId = "session-1",
                    onSelectSession = {},
                    onCreateSession = {},
                    onDeleteSession = {}
                )
            }
        }

        composeRule.onNodeWithTag("session_list")
            .performScrollToNode(hasText("Session 40"))

        composeRule.onNodeWithText("Session 40").assertIsDisplayed()
    }
}
