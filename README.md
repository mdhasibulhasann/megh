# Megh — মেঘ • Android first edition

An Android source project with a locally packaged HTML/CSS/JavaScript interface, a native Java weather bridge, background weather-care notifications, and a basic home-screen widget. This is source code, not an installable APK.

## Included
- Bengali / English switching with Bengali numerals and saved preferences.
- Eight illustrated themes: Cool Blue, Japanese Zen, Banglar Akash, Pixel World, Midnight, Pastel Dream, Retro Game, Minimal Glass.
- Animated clouds, sunlight and rain inside the app, plus reduced-motion control.
- Ten selectable cities including Bangladesh's division capitals, Cumilla and Tokyo.
- Current conditions, next 24 hours, seven days, humidity, wind, precipitation probability and daily maximum UV.
- Forecast-based rain, heat, UV, wind and cold suggestions.
- Explicitly labeled sample forecast; saved forecast fallback with offline status.
- Opt-in native background checks approximately every three hours; daytime notifications at most every six hours. Android battery/network policies and permission settings can delay or prevent delivery.
- Basic Android home-screen widget showing the latest city, temperature and update time. Tap it to open Megh.

## Build on Windows
1. Install Android Studio and its Android SDK. Open this extracted `Megh` folder.
2. This source bundle does not include a Gradle wrapper. Install Gradle 8.9, or configure Android Studio to use a local Gradle 8.9 installation. Use JDK 17.
3. Install Android SDK Platform 35 through SDK Manager. Set `ANDROID_HOME` to your SDK directory or create `local.properties` with `sdk.dir` pointing there.
4. From this folder run `gradle wrapper --gradle-version 8.9`. Then run `gradlew.bat assembleDebug`, or use Android Studio's Build APK action after sync.
5. Install `app/build/outputs/apk/debug/app-debug.apk` on an Android 8.0+ device. Enable notifications when prompted and check battery restrictions.
6. For a production release, create a private signing key and review release signing, privacy disclosure and API terms before publishing.

## Quick interface preview
Open `Preview.html` in a desktop browser. It attempts a live forecast. If your browser or network blocks the API, Settings → Explore a sample forecast shows the design. Browser previews do not provide native notifications or Android widgets.

## Current limits and validation
- APK compilation and Android device/emulator tests have NOT been performed: Android SDK and Gradle are unavailable in the authoring environment.
- JavaScript syntax and functional logic were tested for sample labeling, Bengali digits, language switching, night theme, rain/heat/UV suggestions, cached fallback and error clearing.
- Browser screenshots could not be generated because the environment has no installed Chromium executable. Visual layout needs device review.
- Pixel theme now includes four supplied images with selectable Bangladesh/Japan scenery. Retro remains an early geometric style.
- Home-screen widgets are static, have one resizable layout, and only a limited theme accent. A full themed widget gallery and selection by widget instance remain future work.
- Cities are manually selected. GPS, arbitrary city search, severe official alert feeds, custom notification times and AQI are not implemented.
- Forecast-based suggestions are not official emergency alerts. UV shown is the daily maximum, not the current UV.
- Notifications evaluate one priority suggestion: rain → heat → UV → wind → cold. Forecast checks need connectivity and cannot guarantee a precise lead time.
- Open-Meteo forecast data is fetched by selected coordinates. No account, ads, analytics or location permission is used. Preferences/cache remain on the device.

## Data attribution
Weather data: Open-Meteo (https://open-meteo.com/), CC BY 4.0. This prototype uses the public API. Review commercial API licensing before monetizing or publishing for commercial use.

## Files
- `app/src/main/assets/index.html`: interface, translations, animated SVG landscapes and forecast presentation.
- `app/src/main/java/com/hasibul/megh/`: Android activity/bridge, API client, notification job, widget provider.
- `app/src/main/res/`: widget layout, icon and metadata.
- `Preview.html`: the same interface for browser review.

## Pixel media update (0.2)
Choose Themes → Pixel World → Scenery region: Bangladesh or Japan.

| Supplied picture | File inside app assets | Mapping |
| --- | --- | --- |
| 1 | themes/pixel/bangladesh/clear.png | Bangladesh, mainly clear daytime (WMO 1) |
| 2 | themes/pixel/japan/rainy.png | Japan, rain/drizzle/showers/thunderstorms |
| 3 | themes/pixel/japan/clear.png | Japan, clear or mainly clear daytime (WMO 0/1) |
| 4 | themes/pixel/bangladesh/sunny.png | Bangladesh, clear daytime (WMO 0) |

Clear and sunny are not separate official weather categories here. WMO 0 is treated as sunny daytime; WMO 1 as mainly clear. Japan uses its clear image for both. Night and weather without a supplied picture retain the generated scene. Scenery region is independent of the forecast city.

Images are preserved as supplied and displayed at their full aspect ratio without cropping in the main scene. The images themselves are static; Japan rain has a light animated rain overlay, disabled with Animate scenes off. Home-screen widget artwork is not changed in this update. Native asset serving now returns PNG media correctly.

Browser preview includes a duplicate `themes` folder for relative local paths. To replace artwork, update `app/src/main/assets/themes/pixel/...` and the corresponding preview copy, then rebuild the APK.

## App logo update
The supplied `image.jpg` is preserved unchanged as the app launcher icon and header logo. Adaptive launcher icons inset the complete square artwork on a teal background to protect the lettering from launcher masks. Notification icons retain the monochrome cloud because Android notification small icons require a suitable silhouette. The logo's tagline may be too small to read at launcher-icon size. APK build and device appearance remain untested.

## Online APK build — GitHub Actions
Android Studio is not required for this route. The workflow installs Gradle 8.9, uses Java 17 and the hosted Android SDK, then builds a debug APK. No Gradle wrapper is required on this route.

1. Create a GitHub repository named `Megh`. Choose private if you do not want to publish the source.
2. Extract the downloaded ZIP. Open its inner `Megh` folder.
3. Upload the CONTENTS of that folder into the repository root. Root must contain `.github`, `app`, `build.gradle`, `settings.gradle`, `gradle.properties` and `README.md`. Do not upload the ZIP itself or wrap the project in another `Megh` directory.
4. Verify `.github/workflows/build-apk.yml` appears in GitHub. If your upload omitted the dot-folder, use Add file → Create new file with that exact path and paste the contents of the local workflow file.
5. Commit the upload to the default branch.
6. Open Actions → Build Megh APK → Run workflow. Wait until the job succeeds.
7. On that run's page, find Artifacts and download `Megh-Android-APK`. Extract it to get `app-debug.apk`.
8. Send that APK to the phone and install it. Enable installation from your chosen file manager if Android prompts.

This workflow has been checked structurally but has NOT run on GitHub yet. Compilation may expose errors that need correction; send the failed step's log if it fails.
Debug builds use the runner's generated debug signing key. A later run may require uninstalling the previous build before installation because its signing key can change. Uninstalling removes saved preferences. Use a stable private signing key before ongoing distribution or store publication.
