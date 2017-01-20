<%@ page errorPage="error.jsp"%>
<html>
<head>
	<title>Sign On</title>
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



            <P><strong>Sign In</strong></P>
            <P><strong>Are you a returning customer ?</strong></P>
            <TABLE cellSpacing=0 cellPadding=20 width="100%" border=1>
                <TR>
                    <TD vAlign=top>
                        <FORM name=existingCustomerForm action=signon  method=post>
                            <TABLE cellSpacing=0 cellPadding=5 border=0>
                                <TR>
                                    <TD align=middle colSpan=2><B>Yes.</B></TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Customer Id:</B></TD>
                                    <TD><INPUT size=15 name=customerId> </TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Password:</B></TD>
                                    <TD><INPUT type=password size=15 name=password></TD>
                                </TR>
                                <TR>
                                    <TD align=middle colSpan=2><INPUT type=submit value="Sign In" name=submit></TD>
                                </TR>
                            </TABLE>
                        </FORM>
                    </TD>
                    <TD vAlign=top>
                        <FORM name=newCustomerForm action=createnewaccount method=post>
                            <TABLE cellSpacing=0 cellPadding=5 border=0>
                                <TR>
                                    <TD align=middle colSpan=2><B>No. I would like to sign up for an account.</B></TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Customer Id:</B></TD>
                                    <TD><INPUT size=15 name=id></TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Password:</B></TD>
                                    <TD><INPUT type=password size=15 name=password></TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Password (Repeat):</B></TD>
                                    <TD><INPUT type=password size=15 name=password2></TD>
                                </TR>
                                <TR>
                                    <TD align=middle colSpan=2><INPUT type=submit value="Create New Account" name=submit></TD>
                                </TR>
                            </TABLE>
                        </FORM>
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