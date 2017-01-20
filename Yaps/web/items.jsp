<%@ page errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>YAPS PetStore - Items</title>
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



            <c:if test="${keyword == null}">
                <P><strong>Items for this Product</strong></P>
            </c:if>
            <%-- Else --%>
            <c:if test="${keyword != null}">
                <P><strong>Search Results:</strong></P>
                <P>Items matching any of: ${keyword}</P>
            </c:if>

            <TABLE cellSpacing=0 cellPadding=1 width="100%" border=1>
                <TR>
                    <TD>
                        <TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>

                            <c:forEach items="${itemsDTO}" var="itemDTO">
                                <TR>
                                    <TD>
                                        <A href="<%= request.getContextPath() %>/finditem?itemId=${itemDTO.id}">${itemDTO.name}</A><BR>${itemDTO.productDescription}
                                    </TD>
                                    <TD>
                                        ${itemDTO.unitCost}
                                        <c:if test="${not empty sessionScope.customerDTO}">
                                            <BR>
                                            <A href="<%= request.getContextPath() %>/addtocart?itemId=${itemDTO.id}">Add to Cart</A>
                                        </c:if>
                                    </TD>
                                </TR>
                            </c:forEach>

                        </TABLE>
                    </TD>
                </TR>
            </TABLE>



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