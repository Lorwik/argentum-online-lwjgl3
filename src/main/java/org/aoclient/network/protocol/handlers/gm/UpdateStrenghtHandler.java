package org.aoclient.network.protocol.handlers.gm;

import org.aoclient.engine.game.User;
import org.aoclient.network.PacketBuffer;
import org.aoclient.network.protocol.handlers.PacketHandler;

public class UpdateStrenghtHandler implements PacketHandler {

    @Override
    public void handle(PacketBuffer data) {
        if (data.checkBytes(2)) return;
        data.readByte();
        User.get().setUserStrg(data.readByte());
    }

}
