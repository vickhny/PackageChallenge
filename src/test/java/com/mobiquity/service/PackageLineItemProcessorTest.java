package com.mobiquity.service;

import com.mobiquity.model.Item;
import com.mobiquity.model.PackageLineItem;
import com.mobiquity.utils.PackageLineItemProcessor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Unit test for PackageLineItemProcessor
 *
 */
public class PackageLineItemProcessorTest {
    @Test
    public void testConfigurationParser() {

        PackageLineItem expected = new PackageLineItem();

        expected.setWeightLimit(81);

        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 53.38, 45));
        items.add(new Item(2, 88.62, 98));
        items.add(new Item(3, 78.48, 3));
        items.add(new Item(4, 72.30, 76));
        items.add(new Item(5, 30.18, 9));
        items.add(new Item(6, 46.34, 48));

        expected.setItems(items);

        PackageLineItem parsed = PackageLineItemProcessor.process("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");

        assertNotNull(parsed);
        assertNotNull(parsed.getItems());
        assertEquals(expected.getWeightLimit(), parsed.getWeightLimit());
        assertEquals(expected.getItems().toString(), parsed.getItems().toString());
    }

}
