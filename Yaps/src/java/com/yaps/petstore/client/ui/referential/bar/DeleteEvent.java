package com.yaps.petstore.client.ui.referential.bar;

/**
 * This event is sent to the listening frame when the Delete button of the ManageBar is pressed.
 */
public final class DeleteEvent extends AbstractBarEvent {

    public DeleteEvent(final Object source) {
        super(source);
    }
}
