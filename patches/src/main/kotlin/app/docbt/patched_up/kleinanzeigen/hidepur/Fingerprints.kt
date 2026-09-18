package app.docbt.patched_up.kleinanzeigen.hidepur

import app.morphe.patcher.Fingerprint
import app.morphe.patches.all.misc.resources.ResourceType
import app.morphe.patches.all.misc.resources.resourceLiteral

// Anchors on the ka_gbl_pur string resource to locate the eligibility check
// that gates whether Pur is added to the Compose settings list.
//
// The resource literal alone is not unique: in 2026.37.1 and 2026.38.3 four
// different methods reference it (the settings list builder plus three
// unrelated Compose screens - ad comparison, the ad-free landing page and a
// GDPR bottom sheet). Which one a bare resourceLiteral match returns depends
// on class iteration order, so the class is pinned down by the one trait the
// settings builder alone has: it takes the settings-and-help state as a
// parameter. That package path is not obfuscated, while the class name inside
// it is, so only the prefix is matched.
internal object HidePurEligibilityFingerprint : Fingerprint(
    filters = listOf(
        resourceLiteral(ResourceType.STRING, "ka_gbl_pur"),
    ),
    custom = { method, _ ->
        method.parameterTypes.any {
            it.toString().startsWith("Lebk/ui/preferences/settings/settings_and_help/state/")
        }
    },
)
