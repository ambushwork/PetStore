package com.yaps.petstore.web.taglib;

import com.yaps.petstore.common.constant.CreditCardTypes;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag shows a combo box with all the Credit Card types in a JSP page
 */
public class CreditCardTypesTag extends SimpleTagSupport {

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
        buf.append("<select name='creditCardType'>");

        // Gets all the credit card types
        String creditCardTypes[] = CreditCardTypes.getAll();
        for (int i = 0; i < creditCardTypes.length; i++) {

            // <option>
            buf.append("<option value='").append(creditCardTypes[i]).append("'");

            // Selected credit card type
            if (creditCardTypes[i].equals(getValue())) {
                buf.append(" selected");
            }

            buf.append(">");

            // value
            buf.append(creditCardTypes[i]);

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
