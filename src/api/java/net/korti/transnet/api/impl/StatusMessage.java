package net.korti.transnet.api.impl;

import net.korti.transnet.api.network.IStatusMessage;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @since API Version: 1.0.0
 */
public class StatusMessage implements IStatusMessage {

    private final String unlocalizedMessage;
    private final Object[] messageArg;
    private final boolean successful;

    public StatusMessage(boolean successful, String unlocalizedMessage, Object... messageArg) {
        this.unlocalizedMessage = unlocalizedMessage;
        this.messageArg = messageArg;
        this.successful = successful;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getMessage() {
        return I18n.format(unlocalizedMessage, messageArg);
    }

    @Override
    public String getUnlocalizedMessage() {
        return unlocalizedMessage;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }
}
