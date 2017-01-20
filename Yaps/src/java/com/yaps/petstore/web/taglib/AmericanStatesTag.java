package com.yaps.petstore.web.taglib;

import com.yaps.petstore.common.constant.AmericanStates;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag shows a combo box with all the american states in a JSP page
 */
public class AmericanStatesTag extends SimpleTagSupport {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String value;

    // ======================================
    // =           Business Methods         =
    // ======================================
    public void doTag() throws JspException, IOException {

        StringBuffer buf = new StringBuffer();

        // <select>
        buf.append("<select name='state'>");

        // Gets all the american states
        String states[] = AmericanStates.getAll();
        for (int i = 0; i < states.length; i++) {

            // <option>
            buf.append("<option value='").append(states[i]).append("'");

            // Selected state
            if (states[i].equals(getValue())) {
                buf.append(" selected");
            }

            buf.append(">");

            // value
            buf.append(states[i]);

            // </option>
            buf.append("</option>");
        }

        // </select>
        buf.append("</select>");

        // Display
        getJspContext().getOut().println(buf);

    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
