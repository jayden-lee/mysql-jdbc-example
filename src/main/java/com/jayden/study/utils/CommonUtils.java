package com.jayden.study.utils;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CommonUtils {

    public static Path getBaseDir() {
        Path path = Paths.get(System.getProperty("user.home")).resolve(".mysql-jdbc-example");

        try {
            FileUtils.forceMkdir(path.toFile());
        } catch (IOException e) {
            // ignore
        }

        return path;
    }
}
