package net.korti.transnet.api.network;

/**
 * @since API Version: 1.0.0
 */
public interface IStatusMessage {

    /**
     * @return A localized message about what happened.
     * @since API Version: 1.0.0
     */
    String getMessage();

    /**
     * @return The unlocalized message.
     * @since API Version: 1.0.0
     */
    String getUnlocalizedMessage();

    /**
     * @return If the operation was successful or not.
     * @since API Version: 1.0.0
     */
    boolean isSuccessful();

}
