package com.yage.opencode_client.ui

/**
 * Curated model presets for the model selector, matching iOS implementation.
 * Only these models are shown in the dropdown instead of the full API list.
 */
object ModelPresets {
    val list: List<AppState.ModelOption> = listOf(
        AppState.ModelOption("Opus 4.6 (Copilot)", "github-copilot", "claude-opus-4.6"),
        AppState.ModelOption("Sonnet 4.6 (Copilot)", "github-copilot", "claude-sonnet-4.6"),
        AppState.ModelOption("GPT-5.3 Codex (Copilot)", "github-copilot", "gpt-5.3-codex"),
        AppState.ModelOption("Opus 4.6", "anthropic", "claude-opus-4-6"),
        AppState.ModelOption("Sonnet 4.6", "anthropic", "claude-sonnet-4-6"),
        AppState.ModelOption("GLM-5-turbo", "zai-coding-plan", "glm-5-turbo"),
        AppState.ModelOption("GPT-5.3 Codex", "openai", "gpt-5.3-codex"),
        AppState.ModelOption("GPT-5.4", "openai", "gpt-5.4"),
    )
}
