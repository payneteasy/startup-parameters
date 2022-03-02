[![CircleCI](https://circleci.com/gh/payneteasy/startup-parameters.svg?style=svg)](https://circleci.com/gh/payneteasy/startup-parameters)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.payneteasy%3Astartup-parameters&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.payneteasy%3Astartup-parameters)

Parse startup parameters
==========================


## Features

* supported types: int, String, File, boolean, char, long, Period, Duration, enums, BigDecimal
* thread safe

## Setup with dependency managers

### Maven

```xml
<repositories>
    <repository>
        <id>pne</id>
        <name>payneteasy repo</name>
        <url>https://maven.pne.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.payneteasy</groupId>
        <artifactId>startup-parameters</artifactId>
        <version>1.0-10</version>
    </dependency>
</dependencies>
```

### Gradle

```groovy
compile 'com.payneteasy:startup-parameters:1.0-10'
```

How to use
------------

❶ Create an interface with parameters, [see example](https://github.com/payneteasy/startup-parameters/blob/e2464aa9d9cb9472c0a9cf44717e8bbcaa11801d/src/test/java/com/payneteasy/startup/parameters/IStartupConfig.java#L5)

```java
public interface IStartupConfig {

    @AStartupParameter(name = "WEB_SERVER_PORT", value = "8083")
    int webServerPort();

    @AStartupParameter(name = "WEB_SERVER_CONTEXT", value = "/api")
    String webServerContext();

    @AStartupParameter(name = "REQUEST_LOG_DIR", value = "./logs")
    File getRequestLogDir();
}
```

❷ Create an instance, [see example](https://github.com/payneteasy/startup-parameters/blob/e2464aa9d9cb9472c0a9cf44717e8bbcaa11801d/src/test/java/com/payneteasy/startup/parameters/StartupParametersFactoryTest.java#L13)

```java
    IStartupConfig startupConfig = StartupParametersFactory.getStartupParameters(IStartupConfig.class);
    int port = startupConfig.webServerPort();
```

Pretty log
----------

```java
SLF4JBridgeHandler.removeHandlersForRootLogger();
SLF4JBridgeHandler.install();
```

## License

The Startup Parameters library is licensed under the Apache License 2.0
