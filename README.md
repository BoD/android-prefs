Prefs
===

Android preferences for WINNERS!

![Be a winner!](/illus.jpg?raw=true "Be a winner!")


This little tool generates wrappers for your SharedPreferences, so you can benefit from compile time
verification and code completion in your IDE.  You also get nice singletons for free.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--prefs-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1758)

Usage
---

### 1/ Add the dependencies to your project

```groovy
dependencies {
    /* ... */
    annotationProcessor 'org.jraf:prefs-compiler:1.2.2' // or kapt if you use Kotlin
    implementation 'org.jraf:prefs:1.2.2'
}
```


### 2/ Define your preferences

Use the `@Prefs` annotation on any plain old Java object.  All its (non static) fields will be considered a preference.

For instance:

```java
@Prefs
public class Main {
    /**
     * User login.
     */
    String login;

    /**
     * User password.
     */
    String password;

    @DefaultBoolean(false)
    Boolean isPremium;

    @Name("PREF_AGE")
    Integer age;
}
```

Currently, the accepted types are:
- Boolean
- Float
- Integer
- Long
- String
- Set\<String\>

Optionally, use `@DefaultXxx` and `@Name` annotations (the default default is `null`, and the default name is the name of your field).

You can pass a file name and mode (as per [Context.getSharedPreference()](http://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String, int))) like this:
```java
@Prefs(fileName = "settings", fileMode = Context.MODE_PRIVATE)
```

If you don't, `PreferenceManager.getDefaultSharedPreferences(Context)` will be used.


### 3/ Be a winner!

A class named `<YourClassName>Prefs` will be generated in the same package (at compile time).  Use it like this:

```java
        MainPrefs mainPrefs = MainPrefs.get(this);

        // Put a single value (apply() is automatically called)
        mainPrefs.putAge(42);

        // Put several values in one transaction
        mainPrefs.edit().putLogin("john").putPassword("p4Ssw0Rd").apply();

        // Check if a value is set
        if (mainPrefs.containsLogin()) doSomething();

        // Remove a value
        mainPrefs.removeAge();
        // Or (this has the same effect)
        mainPrefs.putAge(null);

        // Clear all values!
        mainPrefs.clear();
```

Bonus: in Kotlin you can directly use `=`:
```kotlin
        // Put a single value (apply() is automatically called)
        mainPrefs.age = 42
```

License
---

```
Copyright (C) 2015-present Benoit 'BoD' Lubek (BoD@JRAF.org)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

__*Just to be absolutely clear, this license applies to this program itself,
not to the source it will generate!*__
