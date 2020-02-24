package kr.heartpattern.spigotversions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Version information of spigot
 * @property name Name of version
 * @property description Description of version
 * @property refs Git commit reference
 * @property toolsVersion Supported build tool version
 * @property javaVersions Supported java versions
 */
@Serializable
data class Version(
    val name: String,
    val description: String,
    val refs: Refs,
    val information: String? = null,
    val warning: String? = null,
    val toolsVersion: Int? = null,
    val javaVersions: List<Int>? = null
)

/**
 * Git commit references
 * @property buildData BuildData repo
 * @property bukkit Bukkit repo
 * @property craftBukkit CraftBukkit repo
 * @property spigot Spigot repo
 */
@Serializable
data class Refs(
    @SerialName("BuildData") val buildData: String,
    @SerialName("Bukkit") val bukkit: String,
    @SerialName("CraftBukkit") val craftBukkit: String,
    @SerialName("Spigot") val spigot: String
)