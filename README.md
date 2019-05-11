[![Maven Central](https://img.shields.io/maven-central/v/com.payneteasy/startup-parameters.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.payneteasy%22%20AND%20a:%22startup-parameters%22)
[![Build Status](https://travis-ci.org/payneteasy/startup-parameters.svg?branch=master)](https://travis-ci.org/payneteasy/startup-parameters)
[![CircleCI](https://circleci.com/gh/payneteasy/startup-parameters.svg?style=svg)](https://circleci.com/gh/payneteasy/startup-parameters)

Parse startup parameters
==========================


## Features

* supported types: int, String, File
* thread safe

## Setup with dependency managers

### Maven

```xml
<dependency>
  <groupId>com.payneteasy</groupId>
  <artifactId>startup-parameters</artifactId>
  <version>$VERSION</version> <!-- see releases --> 
</dependency>
```

### Gradle

```groovy
compile 'com.payneteasy:startup-parameters:$VERSION'
```

How to use
------------

Create an interface with parameters

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

Create an instance

```java
    IStartupParameters startupConfig = StartupParametersFactory.getStartupParameters(IStartupParameters.class);
    int port = startupConfig.webServerPort()
```


## License

The Startup Parameters library is licensed under the Apache License 2.0
