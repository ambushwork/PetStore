<%@ page errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>YAPS PetStore</title>
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

        <td align="center" width="60%">
        <%--CENTRAL BODY--%>



            <c:if test="${not empty sessionScope.customerDTO}">
                <center>Welcome back <strong>${sessionScope.customerDTO.firstname} ${sessionScope.customerDTO.lastname}</strong></center>
            </c:if>

    		<p>
            <map name="estoremap">
                <area alt="Birds" coords="72,2,280,250" shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=BIRDS"/>
                <area alt="Fish" coords="2,180,72,250" shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=FISH"/>
                <area alt="Dogs" coords="60,250,130,320" shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=DOGS"/>
                <area alt="Reptiles" coords="140,270,210,340" shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=REPTILES"/>
                <area alt="Cats" coords="225,240,295,310" shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=CATS"/>
                <area alt="Birds" coords="280,180,350,250"  shape="RECT"
                	href="<%= request.getContextPath() %>/findproducts?categoryId=BIRDS"/>
            </map>
            <img src="images/splash.gif"
            	border="0" height="355"
            	align="center" usemap="#estoremap" width="350" />
            <p>


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
