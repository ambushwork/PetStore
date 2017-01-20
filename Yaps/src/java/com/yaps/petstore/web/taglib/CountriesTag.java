package com.yaps.petstore.web.taglib;

import com.yaps.petstore.common.constant.Countries;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag shows a combo box with all the countries in a JSP page
 */
public class CountriesTag extends SimpleTagSupport {

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
        buf.append("<select name='country'>");

        // Gets all the countries
        String countries[] = Countries.getAll();
        for (int i = 0; i < countries.length; i++) {

            // <option>
            buf.append("<option value='").append(countries[i]).append("'");

            // Selected country
            if (countries[i].equals(getValue())) {
                buf.append(" selected");
            }

            buf.append(">");

            // value
            buf.append(countries[i]);

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
