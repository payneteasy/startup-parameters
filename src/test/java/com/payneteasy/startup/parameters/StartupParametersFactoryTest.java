package com.payneteasy.startup.parameters;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;

import static org.junit.Assert.*;

public class StartupParametersFactoryTest {

    @Test
    public void getStartupParametersTest() {
        System.setProperty("java.util.logging.config.file", "src/test/resources/logging.properties");

        IStartupConfig startupConfig = StartupParametersFactory.getStartupParameters(IStartupConfig.class);

        File file = startupConfig.getUploadDir();

        assertEquals("./uploadDir", file.getPath());
        assertEquals(8080, startupConfig.webServerPort());
        assertEquals("/", startupConfig.webServerContext());
        assertTrue(startupConfig.useDebugMode());
        assertEquals(9000372036854775807L, startupConfig.endpointId());
        assertEquals(Boolean.FALSE, startupConfig.testLangBoolean());
        assertEquals(Long.valueOf(9000372036854775807L), startupConfig.testLangLong());
        assertEquals(Period.ofYears(1), startupConfig.testPeriod());
        assertEquals(Duration.ofDays(1).plusHours(1), startupConfig.testDuration());
        assertEquals(TestEnum.TWO, startupConfig.testEnum());
        assertEquals(BigDecimal.ONE, startupConfig.testBigDecimal());
    }
}