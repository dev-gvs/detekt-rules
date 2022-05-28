package com.github.dev_gvs.detektrules

import io.gitlab.arturbosch.detekt.api.*
import io.gitlab.arturbosch.detekt.rules.hasAnnotation
import org.jetbrains.kotlin.psi.*

/**
 * Checks that "modifier" argument for Composable function is passed as a last parameter.
 *
 * Wrong:
 * ```
 * @Composable
 * fun MyScreen(
 *     modifier: Modifier,
 *     text: String
 * ) {
 *     Column(
 *         modifier = modifier
 *             .background(Color.LightGray)
 *             .border(1.dp, Color.DarkGray)
 *             .padding(16.dp),
 *         verticalArrangement = Arrangement.spacedBy(32.dp),
 *         horizontalAlignment = Alignment.CenterHorizontally
 *     ) {
 *         Icon(Icons.Outlined.Face, null)
 *         Text(text)
 *     }
 * }
 * ```
 * Correct:
 * ```
 * @Composable
 * fun MyScreen(
 *     modifier: Modifier,
 *     text: String
 * ) {
 *     Column(
 *         verticalArrangement = Arrangement.spacedBy(32.dp),
 *         horizontalAlignment = Alignment.CenterHorizontally,
 *         modifier = modifier
 *             .background(Color.LightGray)
 *             .border(1.dp, Color.DarkGray)
 *             .padding(16.dp)
 *     ) {
 *         Icon(Icons.Outlined.Face, null)
 *         Text(text)
 *     }
 * }
 * ```
 */
class ModifierArgumentPosition(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Reports incorrect modifier argument position",
        Debt.FIVE_MINS,
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        if (function.hasAnnotation("Composable")) {
            function.bodyBlockExpression?.accept(ChildrenWithModifiersVisitor())
        }
    }

    private inner class ChildrenWithModifiersVisitor : DetektVisitor() {
        override fun visitCallExpression(expression: KtCallExpression) {
            val lastArgument = expression.valueArguments.last().getArgumentExpression()
            val modifierArgument = expression.valueArguments.find { arg ->
                arg.getArgumentExpression()?.isModifierChainExpression() == true
            }
            if (lastArgument != null && modifierArgument != null) {
                val argumentBeforeLambda = when {
                    lastArgument is KtLambdaExpression -> expression.valueArguments.getOrNull(
                        expression.valueArguments.lastIndex - 1
                    )
                    else -> expression.valueArguments.last()
                }
                val modifierArgumentIsBeforeLambda = modifierArgument == argumentBeforeLambda
                if (argumentBeforeLambda != null && !modifierArgumentIsBeforeLambda) {
                    report(
                        CodeSmell(
                            issue,
                            Entity.from(modifierArgument),
                            "Modifier argument of composable functions must always be last"
                        )
                    )
                }
            }
            super.visitCallExpression(expression)
        }
    }

    private fun KtExpression.isModifierChainExpression(): Boolean {
        return (this as? KtDotQualifiedExpression)?.text?.let {
            it.startsWith("Modifier") || it.startsWith("modifier")
        } == true
    }
}
