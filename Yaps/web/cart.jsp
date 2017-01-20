<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="error.jsp"%>
<html>
<head>
	<title>YAPS PetStore Shopping Cart</title>
</head>
<body>

<table cellspacing="0" cellpadding="5" width="100%">
    <%--HEADER--%>
	<tr>
		<td colspan="3">
			<jsp:include page="common/header.jsp"/>
		</td>
	</tr>

	<tr>
        <%--NAVIGATION--%>
        <td valign="top" width="20%">
    		<jsp:include page="common/navigation.jsp"/>
    	</td>

        <td align="left" valign="top" width="60%">
        <%--CENTRAL BODY--%>



        <P><strong>Shopping Cart</strong></P>

        <c:choose>
            <%-- Shopping Cart is empty --%>
            <c:when test="${empty requestScope.cartItemsDTO}">
                <P><strong>The Shopping Cart is empty</strong></P>
            </c:when>

            <%-- There are items in the Shopping Cart --%>
            <c:otherwise>
                <TABLE cellSpacing=0 cellPadding=1 width="100%" border=1>
                    <TR>
                        <TD>
                            <TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>

                                <%-- Lists all the items in the shopping cart --%>
                                <c:forEach items="${cartItemsDTO}" var="cartItemDTO">
                                    <form name="cartForm" method="post" action="<%= request.getContextPath() %>/updatecart">
                                        <tr valign="top">
                                            <td width="50%">
                                                <A href="<%= request.getContextPath() %>/finditem?itemId=${cartItemDTO.itemId}">${cartItemDTO.itemName}</A><BR>${cartItemDTO.productDescription}
                                                <input type="hidden" name="itemId" value="${cartItemDTO.itemId}">
                                            </td>
                                            <td>
                                                <A href="<%= request.getContextPath() %>/removefromcart?itemId=${cartItemDTO.itemId}">Remove</A>
                                            </td>
                                            <td>
                                                <input type="submit" value="Update">
                                            </td>
                                            <td align="right">
                                                <input type="text" name="quantity" size="3" maxlength="3" value="${cartItemDTO.quantity}">
                                            </td>
                                            <td align="center">
                                                *
                                            </td>
                                            <td align="right">
                                                 ${cartItemDTO.unitCost}
                                            </td>
                                            <td align="center">
                                                =
                                            </td>
                                            <td align="right">
                                                 ${cartItemDTO.totalCost}
                                            </td>
                                        </tr>
                                    </form>
                                </c:forEach>

                                <%-- Total --%>
                                <tr>
                                    <td colspan="7" align="right">
                                        <b>Total:</b>
                                    </td>
                                    <td bgcolor="#CCCCFF" align="right">
                                        ${requestScope.total}
                                    </td>
                                </tr>
                            </table>
                        </TD>
                    </TR>
                </table>
                <div align="right"><A href="<%= request.getContextPath() %>/checkout">Check Out</A></div>
            </c:otherwise>
        </c:choose>




    <%--FOOTER--%>
    	</td>
        <td></td>
    </tr>
    <tr>
    	<td colspan="3">
    		<jsp:include page="common/footer.jsp"/>
    	</td>
    </tr>
</table>
</body>
</html>