package com.mycompany.app;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.resourcemanager.Azure;
import com.azure.resourcemanager.resources.fluentcore.arm.Region;
import com.azure.resourcemanager.resources.fluentcore.profile.AzureProfile;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        TokenCredential credential = new EnvironmentCredentialBuilder().build();
        Azure azure = Azure.configure().withLogLevel(HttpLogDetailLevel.NONE)
                .authenticate(credential, new AzureProfile(AzureEnvironment.AZURE))
                .withDefaultSubscription();

        final String rgName = "RandomGroup";
        final Region region = Region.US_WEST;

        final boolean isExist = azure.resourceGroups().contain(rgName);

        if (!isExist)
            azure.resourceGroups().define(rgName).withRegion(region).create();

        System.out.println(azure.resourceGroups().getByName(rgName).id());

        if (!isExist)
            azure.resourceGroups().beginDeleteByName(rgName);
    }
}
