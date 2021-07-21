# cleardil-sdk
ClearDil cross-platform SDK

# Integrate our SDK into your Android project
## Adding Cleardil repository to gradle
- First you will need to specify your credentials to Cleardil artifactory repository in the `gradle.properties` file :
```property
artifactory_user=<user>
artifactory_password=<password>
artifactory_contextUrl=https://cleardil.jfrog.io/artifactory
```
- Then add this to your build.gradle to reference our artifactory repository in your project :
```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jfrog.buildinfo:build-info-extractor-gradle:4+"
    }
}

allprojects {
    apply plugin: "com.jfrog.artifactory"
}

artifactory {
    contextUrl = "${artifactory_contextUrl}"   //The base Artifactory URL if not overridden by the publisher/resolver
    resolve {
        repository {
            repoKey = 'cleardil-gradle-release'
            username = "${artifactory_user}"
            password = "${artifactory_password}"
            maven = true
        }
    }
}
```
- Add to your app/build.gradle :
```groovy
android {
    String storageUrl = System.env.FLUTTER_STORAGE_BASE_URL ?: "https://storage.googleapis.com"
    repositories {
        maven {
            url "$storageUrl/download.flutter.io"
        }
        maven {
            url "${artifactory_contextUrl}/cleardil-gradle-release"
        }
    }
}

dependencies {
    implementation 'com.cleardil.sdk:kyc_module:1.0.0'
}
```

## Calling Cleardil SDK in your code
### Basic call
- Add a Flutter activity to your AndroidManifest.xml :
```xml
<activity
            android:name="io.flutter.embedding.android.FlutterActivity"
            android:theme="@android:style/Theme"
            android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
            android:hardwareAccelerated="true"
            android:windowSoftInputMode="adjustResize"
            />
```

- Use the implementation of KycModule builder :
```java
KycModule.builder()
        .withSdkToken("<yourtoken>")
        .build()
        .start(MyActivity.this);
```
This code will instantiate the ClearDil Kyc Module and start it.

### Advanced parameters
- You can specify which type of document is allowed for your clients with theses methods :
```java
- allowPassport()
- allowIdentityCard()
- allowDriverLicense()
```

- Example with the KycModule builder :
```java
KycModule.builder()
        .withSdkToken("<yourtoken>")
        .allowIdentityCard()
        .allowDriverLicense()
        .build()
        .start(MyActivity.this);
```
This code will instantiate the ClearDil Kyc Module allowing only Id CARD & Driver License.

#### Important
Not specifying the Allow methods will allow everything.