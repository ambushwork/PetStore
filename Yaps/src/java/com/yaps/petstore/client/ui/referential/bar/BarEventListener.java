package com.yaps.petstore.client.ui.referential.bar;

import java.util.EventListener;

/**
 * Frames that implements this listener would be able to responde when the followig events occur :<BR>
 * CreateEvent : the create button of the ManageBar has been pressed<BR>
 * DeleteEvent : the delete button of the ManageBar has been pressed<BR>
 * FindEvent   : the find button of the ManageBar has been pressed<BR>
 * UpdateEvent : the modify button of the ManageBar has been pressed<BR>
 * ResetEvent  : the reset button of the ManageBar has been pressed<BR>
 * CloseEvent  : the close button of the ManageBar has been pressed<BR>
 */
public interface BarEventListener extends EventListener {

    public void create(CreateEvent evt);

    public void delete(DeleteEvent evt);

    public void find(FindEvent evt);

    public void update(UpdateEvent evt);

    public void reset(ResetEvent evt);

    public void close(CloseEvent evt);

}
