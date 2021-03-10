package com.mobiquity.service;

import com.mobiquity.model.Item;
import com.mobiquity.model.ProcessedPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vikashkumar
 *
 * The package processor process the packages using knapsack 0-1 algorithm
 */
public class PackageProcessorImpl implements IPackageProcessor {

    @Override
    public ProcessedPackage processor(int capacity, List<Item> items) {

        /*
         * Remove packages which weighs more than the weight limit
         */
        items = items.stream().filter(item -> item.getWeight() <= capacity).collect(Collectors.toList());
        /*
         * Sort the items in ascending order since you would prefer to send a package which weighs less in case there is more than one package with the same price.
         */
        items.sort(Comparator.comparing(Item::getWeight));


        Double[] wt = items.stream().map(Item::getWeight).toArray(Double[]::new);
        Integer[] cost = items.stream().map(Item::getCost).toArray(Integer[]::new);
        Boolean visited[] = new Boolean[items.size()];
        Arrays.fill(visited, Boolean.FALSE);

        /*
         * Compute the recursion tree, keeping track of visited items
         */
        Integer maxCost = maximizeCost(capacity, wt, cost, items.size(), visited);


        List<Item> packedItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            if (visited[i]) {
                packedItems.add(items.get(i));
            }
        }

        /*
         * Items included in the solution should be listed following index natural order
         */
        packedItems.sort(Comparator.comparing(Item::getIndex));

        return new ProcessedPackage(packedItems, maxCost);
    }

    /**
     * @param W    the maximum capacity of the package
     * @param wt   array of items weights
     * @param cost array of items values
     * @param N    total number of items
     * @param visited  keeping track of items which can be processed
     * @return maximum value for the package
     */
    private Integer maximizeCost(Integer W, Double wt[], Integer cost[], Integer N, Boolean visited[]) {

        // Populate base cases
        int[][] mat = new int[N + 1][W + 1];
        for (int r = 0; r < W + 1; r++) {
            mat[0][r] = 0;
        }
        for (int c = 0; c < N + 1; c++) {
            mat[c][0] = 0;
        }

        for (int item = 1; item <= N; item++) {
            for (int capacity = 1; capacity <= W; capacity++) {
                int maxValWithoutCurr = mat[item - 1][capacity];
                int maxValWithCurr = 0;

                Double weightOfCurr = wt[item - 1];

                if (capacity >= weightOfCurr) {
                    maxValWithCurr = cost[item - 1];

                    int remainingCapacity = (int) Math.ceil(capacity - weightOfCurr);
                    maxValWithCurr += mat[item - 1][remainingCapacity];
                }

                mat[item][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr);
            }
        }

        int i;
        double w;
        int res = mat[N][W];

        w = W;
        for (i = N; i > 0 && res > 0; i--) {
            if (res == mat[i - 1][(int) Math.ceil(w)])
                continue;
            else {
                visited[i - 1] = true;
                // Since this cost is included its
                // value is deducted
                res = res - cost[i - 1];
                w = w - wt[i - 1];
                if (w < 0) {
                    break;
                }
            }
        }

        return mat[N][W];
    }
}
