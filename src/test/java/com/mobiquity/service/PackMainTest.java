package com.mobiquity.service;


import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


/**
 * Unit test for Packer
 */
public class PackMainTest {

    private File input;
    private File output;

    @Before
    public void setup() throws URISyntaxException {
        input = getFileFromResource("example_input");
        output = getFileFromResource("example_output");
    }

    @After
    public void teardown() {
        input = null;
        output = null;
    }

    @Test
    public void testMain(){

        Exception exception = assertThrows(APIException.class, () -> {
            Packer.pack("main/testException");
        });
        String expectedMessage = "Input file not readable!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPacker() throws APIException, IOException {

        String result = Packer.pack(input.getPath());
        List<String> resultList = result.lines().collect(Collectors.toList());
        List<String> packageLines = Files.lines(output.toPath()).collect(Collectors.toList());

        assertNotNull(result);
        assertEquals(packageLines.size(), resultList.size());

        for (int i = 0; i < packageLines.size(); i++) {
            assertEquals(packageLines.get(i), resultList.get(i));
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }
}
