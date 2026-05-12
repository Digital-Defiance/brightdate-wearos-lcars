# BrightDate LCARS Watch Face

A Wear OS watch face that displays the current time alongside a live **[BrightDate](https://brightdate.org)** value — the universal decimal time anchored at J2000.0.

![https://github.com/Digital-Defiance/brightdate-wearos-lcars/blob/main/brightdate-lcars-feature-graphic.png](https://github.com/Digital-Defiance/brightdate-wearos-lcars/blob/main/brightdate-lcars-feature-graphic.png)

---

## What it shows

LCARS-inspired layout: deep indigo background, purple accent bars at top and bottom, a subtle starfield, and a three-line BrightDate stack.

| Element | Description |
|---------|-------------|
| **BRIGHTDATE** label | Spaced letter caps in the theme's deep accent |
| **Integer BD** | Big, bold integer portion of the current BrightDate |
| **.fraction** | Five zero-padded fractional digits in the theme's mid accent (~1-minute resolution) |

### BrightDate formula

The watch face computes the BrightDate value in pure XML using Watch Face Format's `[MINUTES_SINCE_EPOCH]` variable:

```
BD      = [MINUTES_SINCE_EPOCH] / 1440.0 − 10957.49919926
integer = floor(BD)
.frac   = round(fract(BD) * 100000) mod 100000   (printed as .%05d)
```

This bakes in the current TAI–UTC offset of **37 seconds** (in place since 2017-01-01). If IERS ever inserts another leap second, a point release will update the constant. The `mod 100000` guards the narrow rounding window where `fract(BD)` would otherwise round up to `1.00000`.

---

## Color themes

Seven built-in themes, selectable in the watch face editor. Each theme provides a three-stop palette: a bright highlight for the integer, a mid accent for the top bar and fractional digits, and a deep accent for the BRIGHTDATE label and bottom bar.

| Theme | Mid accent | Deep accent |
|-------|-----------|-------------|
| Lilac *(default)* | `#7F77DD` | `#534AB7` |
| Gold | `#E8B04C` | `#9C7220` |
| Mint | `#5FD9B0` | `#2E9575` |
| Sky | `#6FB4E6` | `#2F6B96` |
| Rose | `#E68AA0` | `#9C3F5A` |
| Amber | `#F29A3A` | `#8A5612` |
| Mono | `#CCCCCC` | `#888888` |

---

## Requirements

- **Wear OS 5** or later (API level 35+)
- A Wear OS device or emulator

---

## Building

```bash
# Debug build
./gradlew assembleDebug

# Release bundle (requires keystore.properties)
./gradlew bundleRelease
```

### Signing

Create a `keystore.properties` file in the project root (it is gitignored):

```properties
storeFile=path/to/your.keystore
storePassword=...
keyAlias=...
keyPassword=...
```

---

## Project structure

```
app/src/main/
├── AndroidManifest.xml          # Watch Face Format v3, no-code app
└── res/
    ├── raw/watchface.xml        # All layout, colors, and BrightDate logic
    ├── xml/watch_face_info.xml  # Wear OS metadata
    └── values/strings.xml       # Localized labels and theme names
```

The entire watch face is declarative XML — no Kotlin/Java code.

---

## Related

- [BrightDate library (`@brightchain/brightdate`)](brightdate-README.md) — the TypeScript/JavaScript reference implementation
- [brightdate-rust](https://github.com/Digital-Defiance/brightdate-rust) — Rust port and CLI utilities
- [BrightChain](https://github.brightchain.org)

---

## License

See [LICENSE](LICENSE).
