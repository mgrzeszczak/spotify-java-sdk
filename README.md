# spotify java sdk

[![works badge](https://cdn.rawgit.com/nikku/works-on-my-machine/v0.2.0/badge.svg)](https://github.com/nikku/works-on-my-machine)
[![Build Status](https://travis-ci.org/mgrzeszczak/spotify-java-sdk.png)](https://travis-ci.org/mgrzeszczak/spotify-java-sdk)
[![](https://jitpack.io/v/mgrzeszczak/spotify-java-sdk.svg)](https://jitpack.io/#mgrzeszczak/spotify-java-sdk)

Unofficial Spotify API Java SDK.

__IN DEVELOPMENT, BREAKING CHANGES WILL BE HAPPENING__

# Installation

### Gradle
1. Add jitpack repository
    ```
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
    ```
2. Add dependencies on `api` and `model` projects
    ```
    dependencies {
        compile 'com.github.mgrzeszczak.spotify-java-sdk:api:${VERSION}'
        compile 'com.github.mgrzeszczak.spotify-java-sdk:model:${VERSION}'
    }
    ```

### Maven
1. Add jitpack repository
    ```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```
2. Add dependencies on `api` and `model` projects
    ```
    <dependency>
        <groupId>com.github.mgrzeszczak.spotify-java-sdk</groupId>
        <artifactId>api</artifactId>
        <version>${VERSION}</version>
    </dependency>
    <dependency>
        <groupId>com.github.mgrzeszczak.spotify-java-sdk</groupId>
        <artifactId>model</artifactId>
        <version>${VERSION}</version>
    </dependency>
    ```

Version can be checked [__here__](https://jitpack.io/#mgrzeszczak/spotify-java-sdk).


# Examples

See example project.

# License
```
MIT License

Copyright (c) 2017 Maciej Grzeszczak

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```