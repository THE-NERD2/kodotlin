package dev.the_nerd2.kodotlin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.File
import java.net.URLClassLoader
import javax.inject.Inject

@Suppress("unused")
class KodotlinPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateGDExtension", GenerateGDExtension::class.java) {
            it.dependsOn("classes")
            it.inputDir = project.layout.buildDirectory.dir("classes/kotlin/main")
            it.outputDir = project.layout.buildDirectory.dir("gdextension")
        }
    }
}

open class GenerateGDExtension @Inject constructor(private val execOps: ExecOperations): DefaultTask() {
    @get:InputDirectory
    lateinit var inputDir: Provider<Directory>
    @get:OutputDirectory
    lateinit var outputDir: Provider<Directory>
    @TaskAction
    fun generate() {
        val input = inputDir.get().asFile
        val output = outputDir.get().asFile
        if(!output.resolve("Cargo.toml").exists() || !output.resolve("src").exists()) {
            // Crate does not exist or was corrupted. Regenerate it.
            output.deleteRecursively()
            output.mkdirs()
            execOps.exec {
                it.workingDir(output.absolutePath)
                it.commandLine("cargo", "init", "--lib")
            }
            execOps.exec {
                it.workingDir(output.absolutePath)
                it.commandLine("cargo", "add", "godot", "jni")
            }
            val cargoToml = output.resolve("Cargo.toml")
            val cargoTomlLines = cargoToml.readLines().toMutableList()
            cargoTomlLines.add("") // Empty line
            cargoTomlLines.add("[lib]")
            cargoTomlLines.add("crate-type = [\"cdylib\"]")
            cargoToml.writeText(cargoTomlLines.joinToString("\n"))
        }
        val classes = input.walk().filter { it.isFile && it.extension == "class" }.toList()
        val classLoader = URLClassLoader(arrayOf(input.toURI().toURL()))
        classes.forEach {
            val clazz = classLoader.loadClass(it.absolutePath
                .replace(input.absolutePath, "") // Remove path to input directory
                .replace(".class", "") // Remove extension
                .substring(1) // Remove leading path separator
                .replace(File.separatorChar, '.') // Replace path separator
            ).kotlin // KClass instead of Class
            // TODO: process this class to create bindings
        }
    }
}