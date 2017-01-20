<%@ page errorPage="error.jsp"%>
<%@ page import="com.yaps.petstore.common.dto.CustomerDTO"%>
<%@ taglib uri="/WEB-INF/petstore.tld" prefix="petstore" %>

<html>
<head>
	<title>Create Customer</title>
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



            <form name="customerForm" method="post" action="<%= request.getContextPath() %>/updatecustomer">

                <h2>Customer Form</h2>

                <table border="0" cellspacing="4">

	                <!-- Personal information -->
	                <tr>
		                <td colspan="2"><strong>Personal information</strong></td>
	                </tr>
	                <tr>
	                    <td>Customer Id</td>
	                    <td>${requestScope.customerId}</td>
	                    <input type="hidden" name="id" value="${sessionScope.customerDTO.id}">
	                    <input type="hidden" name="password" value="${sessionScope.customerDTO.password}">
	                </tr>
	                <tr>
		                <td>*Firstname :</td>
		                <td><input type="text" name="firstname" value="${sessionScope.customerDTO.firstname}"></td>
	                </tr>
	                <tr>
		                <td>*Lastname :</td>
		                <td><input type="text" name="lastname" value="${sessionScope.customerDTO.lastname}"></td>
	                </tr>
	                <tr>
		                <td>Email :</td>
		                <td><input type="text" name="email" value="${sessionScope.customerDTO.email}"></td>
	                </tr>
	                <tr>
		                <td>Telephone :</td>
		                <td><input type="text" name="telephone" value="${sessionScope.customerDTO.telephone}"></td>
	                </tr>

                	<!-- Address -->
	                <tr>
		                <td colspan="2"><strong>Address</strong></td>
	                </tr>
	                <tr>
		                <td>Street1 :</td>
		                <td><input type="text" name="street1" value="${sessionScope.customerDTO.street1}"></td>
	                </tr>
	                <tr>
		                <td>Street2 :</td>
		                <td><input type="text" name="street2" value="${sessionScope.customerDTO.street2}"></td>
	                </tr>
	                <tr>
		                <td>City :</td>
		                <td><input type="text" name="city" value="${sessionScope.customerDTO.city}"></td>
	                </tr>
	                <tr>
		                <td>State :</td>
		                <td><petstore:americanStates value="${sessionScope.customerDTO.state}"/></td>
	                </tr>
	                <tr>
		                <td>Zipcode :</td>
		                <td><input type="text" name="zipcode" value="${sessionScope.customerDTO.zipcode}"></td>
	                </tr>
	                <tr>
		                <td>Country : </td>
		                <td><petstore:countries  value="${sessionScope.customerDTO.country}"/></td>
	                </tr>

	                <!-- Credit card -->
	                <tr>
		                <td colspan="2"><strong>Credit Card Information</strong></td>
	                </tr>
	                <tr>
		                <td>Type :</td>
		                <td><petstore:creditCardTypes value="${sessionScope.customerDTO.creditCardType}"/></td>
	                </tr>
	                <tr>
		                <td>Number :</td>
		                <td><input type="text" name="creditCardNumber" value="${sessionScope.customerDTO.creditCardNumber}"></td>
	                </tr>
	                <tr>
		                <td>Expiry Date (<small>MM/YY</small>):</td>
		                <td><input type="text" name="creditCardExpiryDate" maxlength="5" value="${sessionScope.customerDTO.creditCardExpiryDate}"></td>
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