# Deployment Guide

This guide explains how to bundle the Sober Companion application for submission to the Google Play Store.

## 1. Generate a Signed Bundle

To submit to the Play Store, you need to generate a signed Android App Bundle (.aab).

### Step 1: Generate a Keystore
If you don't have a keystore, generate one using the keytool command (included with Java/Android SDK):

```bash
keytool -genkey -v -keystore release.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```
*Follow the prompts to set passwords and certificates. Keep this file safe!*

### Step 2: Configure Signing Properties
Create a file named `keystore.properties` in the `app/` directory. **Do not commit this file to version control.**

`app/keystore.properties`:
```properties
storeFile=../release.jks
storePassword=your_store_password
keyAlias=my-key-alias
keyPassword=your_key_password
```
*Note: `storeFile` path is relative to the `app/build.gradle.kts` file. If `release.jks` is in the root, use `../release.jks`.*

### Step 3: Build the Bundle
Run the following command from the project root:

```bash
./gradlew bundleRelease
```

### Step 4: Locate the Artifact
Upon success, the signed bundle will be located at:
`app/build/outputs/bundle/release/app-release.aab`

## 2. Upload to Google Play Console

1.  Go to the [Google Play Console](https://play.google.com/console).
2.  Create a new app or select an existing one.
3.  Navigate to **Production** (or Testing tracks).
4.  Create a new release.
5.  Upload the `app-release.aab` file generated in Step 1.
6.  Fill out the required store listing details, content ratings, and privacy policy.
7.  Submit for review.

## 3. ProGuard / R8
Minification is enabled for the release build. Rules are defined in `app/proguard-rules.pro`.
If the app crashes in release mode but works in debug, check the logs (Logcat) for missing classes and add keep rules to `proguard-rules.pro`.
