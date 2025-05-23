package org.aoclient.network.protocol.handlers.gm;

import org.aoclient.network.PacketBuffer;
import org.aoclient.engine.gui.ImGUISystem;
import org.aoclient.engine.gui.forms.FOptions;
import org.aoclient.engine.gui.forms.FPanelGM;
import org.aoclient.network.protocol.handlers.PacketHandler;

public class ShowGMPanelFormHandler implements PacketHandler {

    @Override
    public void handle(PacketBuffer data) {
        data.readByte();

        ImGUISystem.get().show(new FPanelGM());
    }

}
