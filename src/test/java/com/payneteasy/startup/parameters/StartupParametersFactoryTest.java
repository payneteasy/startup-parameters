package com.payneteasy.startup.parameters;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class StartupParametersFactoryTest {

    @Test
    public void getStartupParametersTest() {
        IStartupConfig startupConfig = StartupParametersFactory.getStartupParameters(IStartupConfig.class);

        File file = startupConfig.getUploadDir();
        assertEquals("./uploadDir", file.getPath());

        int port = startupConfig.webServerPort();
        assertEquals(8080, port);

        String webServerContext = startupConfig.webServerContext();
        assertEquals("/", webServerContext);

        boolean useDebugMode = startupConfig.useDebugMode();
        assertTrue(useDebugMode);

        long endpointId = startupConfig.endpointId();
        assertEquals(9000372036854775807L, endpointId);
    }
}