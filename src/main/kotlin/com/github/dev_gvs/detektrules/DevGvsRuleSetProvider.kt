package com.github.dev_gvs.detektrules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class DevGvsRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "dev-gvs"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                ModifierArgumentPosition(config),
            ),
        )
    }
}
