<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table cellspacing="0" width="100%">
    <tr>
        <td align="left" valign="middle">
    		<a href="<%= request.getContextPath() %>/index.jsp"><img border="0" src="images/banner_logo.gif"/></a>
        </td>
        <td align="right" valign="middle">
            <form action="<%= request.getContextPath() %>/searchitems">
                <input type="text" name="keyword">
                <input type="submit" value="Search">
            </form>
            <br>
            <c:choose>
                <c:when test="${empty sessionScope.customerDTO}">
                    <a href="<%= request.getContextPath() %>/signon.jsp">Sign on</a>
                </c:when>
                <c:otherwise>
            		<a href="<%= request.getContextPath() %>/customer.jsp">Account</a> |
            		<a href="<%= request.getContextPath() %>/viewcart">Cart</a> |
                    <a href="<%= request.getContextPath() %>/signoff">Sign off</a>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<table cellspacing="0" width="100%">
    <tr>
        <td>
            <hr noShade SIZE=1>
        </td>
    </tr>
</table>
