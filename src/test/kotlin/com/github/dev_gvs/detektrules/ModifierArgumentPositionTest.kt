package com.github.dev_gvs.detektrules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class ModifierArgumentPositionTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `should report when incorrect position`() {
        // language=kotlin
        val code = """
            @Composable
            fun MyScreen(
                modifier: Modifier,
                text: String,
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .border(1.dp, Color.DarkGray)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Outlined.Face, null)
                    Text(text = text, modifier = Modifier.padding(16.dp), color = Color.White)
                }
            }
        """.trimIndent()
        val findings = ModifierArgumentPosition(Config.empty).compileAndLintWithContext(env, code)
        assertThat(findings).hasSize(2)
    }

    @Test
    fun `should not report when in last position`() {
        // language=kotlin
        val code = """
            @Composable
            fun MyScreen(
                modifier: Modifier,
                text: String,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .border(1.dp, Color.DarkGray)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Outlined.Face, null)
                    Text(text = text, color = Color.White, modifier = Modifier.padding(16.dp))
                }
            }
        """.trimIndent()
        val findings = ModifierArgumentPosition(Config.empty).compileAndLintWithContext(env, code)
        assertThat(findings).isEmpty()
    }
}
