package org.ig2i.chat2i.utils;

import org.apache.log4j.Logger;

public class TestSlf4j {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(TestSlf4j.class);
        logger.info("Hello World");
        logger.debug("Debug");
        logger.warn("Warning");
        logger.error("Error");
    }
}
