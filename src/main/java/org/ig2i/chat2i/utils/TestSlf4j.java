package org.ig2i.chat2i.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestSlf4j {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(TestSlf4j.class);
        logger.info("Hello World");
        logger.debug("Debug");
        logger.warn("Warning");
        logger.error("Error");
    }
}
