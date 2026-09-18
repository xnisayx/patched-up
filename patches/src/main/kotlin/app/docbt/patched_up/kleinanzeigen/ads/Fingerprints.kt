package app.docbt.patched_up.kleinanzeigen.ads

import app.morphe.patcher.Fingerprint

// Liberty init method: initializes the ad/analytics SDK and Microsoft Clarity.
// The method itself is ebk/core/liberty/d.g(ArrayList) and has kept that exact
// name, signature and void return across 2026.37.1 and 2026.38.3.
//
// The previous anchor, "KEY_LIBERTY_REFRESH_INTERVAL", moved out of it in
// 2026.38.3 into a new suspend config builder (d.s(List, Continuation)) that
// returns the Liberty config object. The fingerprint followed the string, so
// the patch injected return-void into a method whose return type is Object.
// That is unverifiable bytecode: the class fails verification and the app dies
// at startup. Anchoring on the Clarity log line keeps us on the init method,
// which is where the string has stayed in both versions.
//
// The return-type guard is deliberate: if this method ever becomes non-void
// too, the patch must fail loudly at patch time instead of producing an APK
// that crashes on launch.
internal object LibertyInitFingerprint : Fingerprint(
    strings = listOf("MicrosoftClarityService: Failed to initialize Microsoft Clarity SDK"),
    custom = { method, _ -> method.returnType == "V" },
)
