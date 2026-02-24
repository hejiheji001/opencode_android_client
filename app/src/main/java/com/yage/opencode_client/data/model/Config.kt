package com.yage.opencode_client.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonElement

@Serializable
data class HealthResponse(
    val healthy: Boolean,
    val version: String? = null
)

@Serializable
data class ProvidersResponse(
    val providers: List<ConfigProvider> = emptyList(),
    val default: DefaultProvider? = null
)

@Serializable
data class ConfigProvider(
    val id: String = "",
    val name: String? = null,
    val models: Map<String, ProviderModel> = emptyMap()
)

@Serializable
data class ProviderModel(
    val id: String = "",
    val name: String? = null,
    @SerialName("providerID") val providerId: String? = null,
    @SerialName("providerId") val providerIdAlt: String? = null,
    val limit: ProviderModelLimit? = null
) {
    val resolvedProviderId: String? get() = providerId ?: providerIdAlt
}

@Serializable
data class ProviderModelLimit(
    val context: Int? = null,
    val input: Int? = null,
    val output: Int? = null
)

@Serializable
data class DefaultProvider(
    @SerialName("providerID") val providerId: String,
    @SerialName("modelID") val modelId: String
)
