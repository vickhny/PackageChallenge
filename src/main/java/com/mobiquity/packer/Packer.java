package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.utils.PackageLineItemProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Packer is responsible for packaging
 */
public class Packer {

    public static String pack(String filePath) throws APIException {

        Path path = Paths.get(filePath);

        try {
            if (Files.isReadable(path)) {
                List<String> packageLines = Files.lines(path).collect(Collectors.toList());
                StringBuilder stringBuilder = new StringBuilder();
                for (String line : packageLines) {
                    stringBuilder.append(PackageLineItemProcessor.processLine(line) + "\n");
                }
                return stringBuilder.toString();
            } else {
                throw new APIException("Input file not readable!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
