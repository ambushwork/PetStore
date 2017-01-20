<%@ page errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/petstore.tld" prefix="petstore" %>
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

        <td align="left" width="60%">
        <%--CENTRAL BODY--%>



            <jsp:useBean id="customerDTO" class="com.yaps.petstore.common.dto.CustomerDTO" scope="session" />

            <h2>Your Account Information</h2>

            <table id="customerProfile" border="0" cellspacing="4">

                <!-- Personal information -->
                <tr>
                    <td colspan="2"><strong>Personal information</strong></td>
	            </tr>
	            <tr>
		            <td>Firstname :</td><td>${customerDTO.firstname}</td>
	            </tr>
	            <tr>
		            <td>Lastname :</td><td>${customerDTO.lastname}</td>
	            </tr>
	            <tr>
		            <td>Email :</td><td>${customerDTO.email}</td>
	            </tr>
	            <tr>
		            <td>Telephone :</td><td>${customerDTO.telephone}</td>
	            </tr>

                	<!-- Address -->
	            <tr>
		            <td colspan="2"><strong>Address</strong></td>
	            </tr>
	            <tr>
		            <td>Street1 :</td><td>${customerDTO.street1}</td>
	            </tr>
	            <tr>
		            <td>Street2 :</td><td>${customerDTO.street2}</td>
	            </tr>
	            <tr>
		            <td>City :</td><td>${customerDTO.city}</td>
	            </tr>
	            <tr>
		            <td>State :</td><td>${customerDTO.state}</td>
	            </tr>
	            <tr>
		            <td>Zipcode :</td><td>${customerDTO.zipcode}</td>
	            </tr>
	            <tr>
		            <td>Country :</td><td>${customerDTO.country}</td>
	            </tr>

	            <!-- Credit card -->
	            <tr>
		            <td colspan="2"><strong>Credit Card Information</strong></td>
	            </tr>
	            <tr>
		            <td>Type :</td><td>${customerDTO.creditCardType}</td>
	            </tr>
	            <tr>
		            <td>Number :</td><td>${customerDTO.creditCardNumber}</td>
	            </tr>
	            <tr>
		            <td>Expiry Date (<small>MM/YY</small>):</td><td>${customerDTO.creditCardExpiryDate}</td>
	            </tr>
            </table>
            <p><a href="updatecustomer.jsp">Edit Your Account Information</a></p>


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
