package com.yaps.petstore.client.ui.referential.bar;

/**
 * This event is sent to the listening frame when the Update button of the ManageBar is pressed.
 */
public final class UpdateEvent extends AbstractBarEvent {

    public UpdateEvent(final Object source) {
        super(source);
    }
}
