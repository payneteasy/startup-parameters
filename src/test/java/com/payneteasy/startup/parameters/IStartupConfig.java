package com.payneteasy.startup.parameters;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;

public interface IStartupConfig {

    @AStartupParameter(name = "WEB_SERVER_PORT", value = "8080")
    int webServerPort();

    @AStartupParameter(name = "WEB_SERVER_CONTEXT", value = "/")
    String webServerContext();

    @AStartupParameter(name = "UPLOAD_DIR", value = "./uploadDir")
    File getUploadDir();

    @AStartupParameter(name = "USE_DEBUG_MODE", value = "true")
    boolean useDebugMode();

    @AStartupParameter(name = "ENDPOINT_ID", value = "9000372036854775807")
    long endpointId();

    @AStartupParameter(name = "TEST_LANG_BOOLEAN", value = "false")
    Boolean testLangBoolean();

    @AStartupParameter(name = "TEST_LANG_LONG", value = "9000372036854775807")
    Long testLangLong();

    @AStartupParameter(name = "TEST_PERIOD", value = "P1Y")
    Period testPeriod();

    @AStartupParameter(name = "TEST_DURATION", value = "P1DT1H")
    Duration testDuration();

    @AStartupParameter(name = "TEST_ENUM", value = "TWO")
    TestEnum testEnum();

    @AStartupParameter(name = "TEST_BIG_DECIMAL", value = "1")
    BigDecimal testBigDecimal();

    @AStartupParameter(name = "TEST_CHAR", value = "h")
    char testChar();
}
