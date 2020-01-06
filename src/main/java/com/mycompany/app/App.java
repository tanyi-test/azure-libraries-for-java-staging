package com.mycompany.app;

import java.io.File;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.LogLevel;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
        Azure azure = Azure.configure().withLogLevel(LogLevel.NONE).authenticate(credFile).withDefaultSubscription();

        final String rgName = "RandomGroup";
        final Region region = Region.US_WEST;

        final Boolean isExist = azure.resourceGroups().contain(rgName);

        if (!isExist)
            azure.resourceGroups().define(rgName).withRegion(region).create();

        System.out.println(azure.resourceGroups().getByName(rgName).id());

        if (!isExist)
            azure.resourceGroups().beginDeleteByName(rgName);
    }
}
