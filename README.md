[![maven](https://maven-badges.herokuapp.com/maven-central/com.payneteasy/startup-parameters/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.payneteasy/startup-parameters)
[![Build Status](https://travis-ci.org/evsinev/startup-parameters.svg?branch=master)](https://travis-ci.org/evsinev/startup-parameters)
[![CircleCI](https://circleci.com/gh/evsinev/startup-parameters.svg?style=svg)](https://circleci.com/gh/evsinev/startup-parameters)

Parse startup parameters
==========================


## Features

* supported types: amount, date, time, text, BCD, bytes
* thread safe (provides immutable container BerTlv)

## Setup with dependency managers

### Maven

```xml
<dependency>
  <groupId>com.payneteasy</groupId>
  <artifactId>startup-parameters</artifactId>
  <version>$VERSION</version>
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

    @AStartupParameter(name = "WEB_SERVER_CONTEXT", value = "/telpo-tms")
    String webServerContext();

    @AStartupParameter(name = "TELPO_CONFIG_DIR", value = "../../telpo-config/mini")
    File getTelpoConfigDir();

    @AStartupParameter(name = "REQUEST_LOG_DIR", value = "./logs")
    File getRequestLogDir();
}
```

Create an instance

```java
    IStartupParameters startupConfig = StartupParametersFactory.getStartupParameters(IStartupParameters.class);
```


## License

The BerTlv framework is licensed under the Apache License 2.0
