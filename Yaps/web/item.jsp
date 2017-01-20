<%@ page errorPage="error.jsp"%>
<%@ page import="com.yaps.petstore.common.dto.ItemDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>YAPS PetStore - Item</title>
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



            <jsp:useBean id="itemDTO" class="com.yaps.petstore.common.dto.ItemDTO" scope="request" />

            <P><strong>${itemDTO.name}</strong></P>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
                <TR>
                    <TD>
                        <TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
                            <TR>
                                <TD width="20%"><IMG src="images/${itemDTO.imagePath}"></TD>
                                <TD valign="top">
                                    <strong>Unit Cost: </strong>$${itemDTO.unitCost}
                                    <c:if test="${not empty sessionScope.customerDTO}">
                                        <BR><BR>
                                        <A href="<%= request.getContextPath() %>/addtocart?itemId=${itemDTO.id}">Add to Cart</A>
                                    </c:if>
                                </TD>
                            </TR>

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