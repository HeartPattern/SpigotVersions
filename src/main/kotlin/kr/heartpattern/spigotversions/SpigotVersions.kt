package kr.heartpattern.spigotversions

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.regex.Pattern

private val pattern = Pattern.compile("""<a href="(.*)\.json">""")
private val json = Json(JsonConfiguration.Stable)

/**
 * Request list of spigot versions
 * @return List of version name
 */
suspend fun getSpigotVersionList(): List<String> {
    val body = Fuel.get("https://hub.spigotmc.org/versions/").awaitString()
    val list = LinkedList<String>()
    val matcher = pattern.matcher(body)
    while (matcher.find()) {
        list += matcher.group(1)
    }
    return list
}

/**
 * Request list of spigot versions
 * @return CompletableFuture which return list of version name
 */
fun getSpigotVersionListFuture(): CompletableFuture<List<String>>{
    return GlobalScope.async{getSpigotVersionList()}.asCompletableFuture()
}

/**
 * Request spigot versions
 * @param version Name of version
 * @return Version information
 */
suspend fun getSpigotVersion(version: String): Version{
    val body = Fuel.get("https://hub.spigotmc.org/versions/$version.json").awaitString()
    return json.parse(Version.serializer(), body)
}

/**
 * Request spigot versions
 * @param version Name of version
 * @return CompletableFuture which return version information
 */
fun getSpigotVersionFuture(version: String): CompletableFuture<Version>{
    return GlobalScope.async{getSpigotVersion(version)}.asCompletableFuture()
}