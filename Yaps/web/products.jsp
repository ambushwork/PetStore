<%@ page errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>YAPS PetStore - Products</title>
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



            <P><strong>Products for this Category</strong></P>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" border=1>
                <TR>
                    <TD>
                        <TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>

                            <c:forEach items="${productsDTO}" var="productDTO">
                                <TR>
                                    <TD>
                                        <A href="<%= request.getContextPath() %>/finditems?productId=${productDTO.id}">${productDTO.name}</A><BR>${productDTO.description}
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