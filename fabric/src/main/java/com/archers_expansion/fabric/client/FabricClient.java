package com.archers_expansion .fabric.client;

import com.archers_expansion.client.ArchersExpansionModClient;
import net.fabricmc.api.ClientModInitializer;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArchersExpansionModClient.init();
    }
}
