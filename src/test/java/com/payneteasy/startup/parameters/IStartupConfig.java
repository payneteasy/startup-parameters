package com.payneteasy.startup.parameters;

import java.io.File;

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
}
