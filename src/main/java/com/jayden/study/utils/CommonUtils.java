package com.jayden.study.utils;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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

    public static Random random() {
        return new Random(System.currentTimeMillis());
    }
}
