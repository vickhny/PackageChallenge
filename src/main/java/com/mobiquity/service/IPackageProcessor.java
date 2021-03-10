package com.mobiquity.service;

import com.mobiquity.model.Item;
import com.mobiquity.model.ProcessedPackage;

import java.util.List;

/**
 * @author vikashkumar
 */
public interface IPackageProcessor {

    ProcessedPackage processor(int capacity, List<Item> items);
}
