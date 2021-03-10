package com.mobiquity.utils;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackageLineItem;
import com.mobiquity.model.ProcessedPackage;
import com.mobiquity.service.IPackageProcessor;
import com.mobiquity.service.PackageProcessorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An utility class to read and process input data into PackageLineItem
 *
 * @author vikashkumar
 */
public class PackageLineItemProcessor {

    private static final IPackageProcessor processor = new PackageProcessorImpl();

    /**
     *
     * @param line contains weight limit and package items
     * @return indexes of package to maximize the cost within given weight limit
     * @throws APIException
     */
    public static String processLine(String line) throws APIException {

        PackageLineItem packageLineItem = process(line);

        if (packageLineItem == null) {
            throw new APIException("Invalid input file!");
        }

        ProcessedPackage processedPackage = processor.processor(packageLineItem.getWeightLimit(), packageLineItem.getItems());

        String solutionItemsList;

        if (processedPackage.getItems() != null && !processedPackage.getItems().isEmpty()) {
            solutionItemsList = processedPackage.getItems().stream().map(i -> i.getIndex().toString()).collect(Collectors.joining(","));
        } else {
            solutionItemsList = "-";
        }

        return solutionItemsList;
    }

    /**
     * @param line contains raw input data
     * @return a PackageLineItem contains structured processed packaged details with list of package items and weight limit
     */
    public static PackageLineItem process(String line) {

        PackageLineItem packageLineItem = null;

        String[] configurationParts = line.split(" : ");

        try {

            if (configurationParts.length == 2) {

                Integer capacity = Integer.parseInt(configurationParts[0]);

                String[] itemsParts = configurationParts[1].split("\\s+");

                if (itemsParts.length > 0) {

                    List<Item> itemsList = new ArrayList<>();

                    for (String itemString : itemsParts) {

                        String[] itemParts = itemString.replace("(", "").replace(")", "").split(",");
                        if (itemParts.length == 3) {

                            itemsList.add(new Item(
                                    Integer.parseInt(itemParts[0]),
                                    Double.parseDouble(itemParts[1]),
                                    Integer.parseInt(itemParts[2].replace("€", ""))
                            ));

                        } else {
                            itemsList.clear();
                            break;
                        }
                    }

                    if (!itemsList.isEmpty()) {
                        packageLineItem = new PackageLineItem(capacity, itemsList);
                    }

                }

            }
        } catch (NumberFormatException e) {
            packageLineItem = null;
        }

        return packageLineItem;
    }

}
