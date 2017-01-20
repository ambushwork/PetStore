<%@ page errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/petstore.tld" prefix="petstore" %>
<html>
<head>
	<title>YAPS PetStore - Create Customer</title>
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

        <td align="left" width="60%">
        <%--CENTRAL BODY--%>



            <form name="customerForm" method="post" action="<%= request.getContextPath() %>/createcustomer">

                <h2>Create Customer Form</h2>

                <table border="0" cellspacing="4">

	                <!-- Personal information -->
	                <tr>
		                <td colspan="2"><strong>Personal information</strong></td>
	                </tr>
	                <tr>
	                    <td>Customer Id</td>
	                    <td>${requestScope.id}</td>
	                    <input type="hidden" name="id" value="${requestScope.id}">
	                    <input type="hidden" name="password" value="${requestScope.password}">
	                </tr>
	                <tr>
		                <td>*Firstname :</td>
		                <td><input type="text" name="firstname" value=""></td>
	                </tr>
	                <tr>
		                <td>*Lastname :</td>
		                <td><input type="text" name="lastname" value=""></td>
	                </tr>
	                <tr>
		                <td>Email :</td>
		                <td><input type="text" name="email" value=""></td>
	                </tr>
	                <tr>
		                <td>Telephone :</td>
		                <td><input type="text" name="telephone" value=""></td>
	                </tr>

                	<!-- Address -->
	                <tr>
		                <td colspan="2"><strong>Address</strong></td>
	                </tr>
	                <tr>
		                <td>Street1 :</td>
		                <td><input type="text" name="street1" value=""></td>
	                </tr>
	                <tr>
		                <td>Street2 :</td>
		                <td><input type="text" name="street2" value=""></td>
	                </tr>
	                <tr>
		                <td>City :</td>
		                <td><input type="text" name="city" value=""></td>
	                </tr>
	                <tr>
		                <td>State :</td>
		                <td><petstore:americanStates/></td>
	                </tr>
	                <tr>
		                <td>Zipcode :</td>
		                <td><input type="text" name="zipcode" value=""></td>
	                </tr>
	                <tr>
		                <td>Country :</td>
		                <td><petstore:countries/></td>
	                </tr>

	                <!-- Credit card -->
	                <tr>
		                <td colspan="2"><strong>Credit Card Information</strong></td>
	                </tr>
	                <tr>
		                <td>Type :</td>
		                <td><petstore:creditCardTypes/></td>
	                </tr>
	                <tr>
		                <td>Number :</td>
		                <td><input type="text" name="creditCardNumber" value=""></td>
	                </tr>
	                <tr>
		                <td>Expiry Date (<small>MM/YY</small>):</td>
		                <td><input type="text" name="creditCardExpiryDate" maxlength="5" value=""></td>
	                </tr>
                </table>
                <p>
                <input type="submit" name="Submit" value="Submit">
            </form>



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