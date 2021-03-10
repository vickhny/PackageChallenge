package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

public class PackMain {

    public static void main(String[] args) {
        if (args[0] != null) {
            try {
                System.out.println(Packer.pack(args[0]));
            } catch (APIException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Please enter a valid filepath!");
        }
    }
}
