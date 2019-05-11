[![maven](https://maven-badges.herokuapp.com/maven-central/com.payneteasy/startup-parameters/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.payneteasy/startup-parameters)
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
public interface IStartupParameters {

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
```


## License

The Startup Parameters library is licensed under the Apache License 2.0
