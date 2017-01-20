package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * This servlet takes the exception message from the HttpServletRequest and displays it
 * creating dynamicaly a HTML page
 */
public final class ErrorServlet extends HttpServlet {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Entry point method         =
    // ======================================
    public void service(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final String mname = "service";
        Trace.entering(_cname, mname);

        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>YAPS Error</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1><center>Error !</center></h1>");
        out.print(request.getParameter("exception"));
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
