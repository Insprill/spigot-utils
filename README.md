[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache 2.0 License][license-shield]][license-url]



<h1 align="center">Spigot Utils</h1>
<p align="center">
  A small library of commonly used functions in Spigot plugins
  <br />
  <a href="https://javadoc.io/doc/net.insprill/spigot-utils"><strong>View Javadocs »</strong></a>
  <br />
  <br />
  <a href="https://github.com/Insprill/spigot-utils/issues">Report Bugs</a>
  ·
  <a href="https://github.com/Insprill/spigot-utils/issues">Request Features</a>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#compiling">Compiling</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

<!-- USAGE EXAMPLES -->

## Usage

### Implementing in your project

Make sure to replace `VERSION` in the below examples with the version below:  

[![Maven Central][maven-central-shield]][maven-central-url]
#### Maven

```xml
<dependency>
    <groupId>net.insprill</groupId>
    <artifactId>spigot-utils</artifactId>
    <version>VERSION</version>
</dependency>
```

#### Gradle (Groovy)
```groovy
dependencies {
    implementation 'net.insprill:spigot-utils:VERSION'
}
```

#### Gradle (Kotlin)
```kotlin
dependencies {
    implementation("net.insprill:spigot-utils:VERSION")
}
```



## Compiling

To compile spigot-utils, you need JDK 21 or higher and an internet connection.  
Clone this repo, then run `./gradlew build` from your terminal.  
You can find the compiled jar in the `build/libs` directory.  
If you wish to install it to your local Maven repository, run `./gradlew publishToMavenLocal` after compiling.



<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes.
4. Stage your changes (`git add .`)
5. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
6. Push to the Branch (`git push origin feature/AmazingFeature`)
7. Open a Pull Request

<!-- LICENSE -->

## License

Distributed under the Apache 2.0 License. See [`LICENSE`][license-url] for more information.




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/Insprill/spigot-utils.svg?style=for-the-badge
[contributors-url]: https://github.com/Insprill/spigot-utils/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Insprill/spigot-utils.svg?style=for-the-badge
[forks-url]: https://github.com/Insprill/spigot-utils/network/members
[stars-shield]: https://img.shields.io/github/stars/Insprill/spigot-utils.svg?style=for-the-badge
[stars-url]: https://github.com/Insprill/spigot-utils/stargazers
[issues-shield]: https://img.shields.io/github/issues/Insprill/spigot-utils.svg?style=for-the-badge
[issues-url]: https://github.com/Insprill/spigot-utils/issues
[license-shield]: https://img.shields.io/github/license/Insprill/spigot-utils.svg?style=for-the-badge
[license-url]: https://github.com/Insprill/spigot-utils/blob/master/LICENSE
[maven-central-shield]: https://img.shields.io/maven-central/v/net.insprill/spigot-utils
[maven-central-url]: https://mvnrepository.com/artifact/net.insprill/spigot-utils
