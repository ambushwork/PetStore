package com.yaps.petstore.client.ui.referential.bar;

/**
 * This event is sent to the listening frame when the Create button of the ManageBar is pressed.
 */
public final class CreateEvent extends AbstractBarEvent {

    public CreateEvent(final Object source) {
        super(source);
    }
}
