package com.yaps.petstore.client.ui.referential.bar;

/**
 * This event is sent to the listening frame when the Reset button of the ManageBar is pressed.
 */
public final class ResetEvent extends AbstractBarEvent {

    public ResetEvent(final Object source) {
        super(source);
    }
}
