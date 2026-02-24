package com.yage.opencode_client

import com.yage.opencode_client.ui.AppState
import com.yage.opencode_client.data.model.*
import org.junit.Assert.*
import org.junit.Test

class AppStateTest {
    
    @Test
    fun `AppState default values`() {
        val state = AppState()
        
        assertFalse(state.isConnected)
        assertFalse(state.isConnecting)
        assertNull(state.serverVersion)
        assertTrue(state.sessions.isEmpty())
        assertNull(state.currentSessionId)
        assertTrue(state.sessionStatuses.isEmpty())
        assertTrue(state.messages.isEmpty())
        assertEquals(6, state.messageLimit)
        assertFalse(state.isLoadingMessages)
        assertTrue(state.agents.isEmpty())
        assertEquals("build", state.selectedAgentName)
        assertEquals(0, state.selectedModelIndex)
        assertNull(state.providers)
        assertTrue(state.pendingPermissions.isEmpty())
        assertEquals("", state.inputText)
        assertNull(state.error)
    }
    
    @Test
    fun `currentSession returns correct session`() {
        val session1 = Session(id = "s1", directory = "/project1")
        val session2 = Session(id = "s2", directory = "/project2")
        
        val state = AppState(
            sessions = listOf(session1, session2),
            currentSessionId = "s2"
        )
        
        assertEquals(session2, state.currentSession)
    }
    
    @Test
    fun `currentSession returns null when no session selected`() {
        val session1 = Session(id = "s1", directory = "/project1")
        
        val state = AppState(
            sessions = listOf(session1),
            currentSessionId = null
        )
        
        assertNull(state.currentSession)
    }
    
    @Test
    fun `currentSessionStatus returns correct status`() {
        val status1 = SessionStatus(type = "idle")
        val status2 = SessionStatus(type = "busy")
        
        val state = AppState(
            sessionStatuses = mapOf("s1" to status1, "s2" to status2),
            currentSessionId = "s2"
        )
        
        assertEquals(status2, state.currentSessionStatus)
    }
    
    @Test
    fun `isCurrentSessionBusy returns true when busy`() {
        val state = AppState(
            sessionStatuses = mapOf("s1" to SessionStatus(type = "busy")),
            currentSessionId = "s1"
        )
        
        assertTrue(state.isCurrentSessionBusy)
    }
    
    @Test
    fun `isCurrentSessionBusy returns false when idle`() {
        val state = AppState(
            sessionStatuses = mapOf("s1" to SessionStatus(type = "idle")),
            currentSessionId = "s1"
        )
        
        assertFalse(state.isCurrentSessionBusy)
    }
    
    @Test
    fun `isCurrentSessionBusy returns false when no status`() {
        val state = AppState(currentSessionId = "s1")
        
        assertFalse(state.isCurrentSessionBusy)
    }
    
    @Test
    fun `visibleAgents filters correctly`() {
        val agents = listOf(
            AgentInfo(name = "Visible1", mode = "primary", hidden = false),
            AgentInfo(name = "Hidden", mode = "primary", hidden = true),
            AgentInfo(name = "SubAgent", mode = "subagent", hidden = false),
            AgentInfo(name = "Visible2", mode = "all", hidden = false)
        )
        
        val state = AppState(agents = agents)
        
        assertEquals(2, state.visibleAgents.size)
        assertEquals("Visible1", state.visibleAgents[0].name)
        assertEquals("Visible2", state.visibleAgents[1].name)
    }
}
