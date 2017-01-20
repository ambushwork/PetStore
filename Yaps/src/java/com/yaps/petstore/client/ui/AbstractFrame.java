package com.yaps.petstore.client.ui;

import javax.swing.*;

public class AbstractFrame extends JFrame {

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    protected String getCname() {
        return _cname;
    }
}
