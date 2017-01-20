<table border="0" cellspacing="0" cellpadding="0" width="100%">
<%--<table cellspacing="0" cellpadding="5" width="100%">--%>
    <TR>
        <TD align=middle bgColor=#336666><FONT color=#ffffff><strong>Pets</strong></FONT> </TD>
    </TR>
    <TR>
        <TD bgColor=#336666>
            <TABLE cellSpacing=1 cellPadding=5 width="100%" border=0>
                <TR>
                    <TD bgColor=#ffffff>
                        <A href="<%= request.getContextPath() %>/findproducts?categoryId=BIRDS">Birds</A><BR>
                        <A href="<%= request.getContextPath() %>/findproducts?categoryId=CATS">Cats</A><BR>
                        <A href="<%= request.getContextPath() %>/findproducts?categoryId=DOGS">Dogs</A><BR>
                        <A href="<%= request.getContextPath() %>/findproducts?categoryId=FISH">Fish</A><BR>
                        <A href="<%= request.getContextPath() %>/findproducts?categoryId=REPTILES">Reptiles</A><BR>
                    </TD>
                </TR>
            </TABLE>
        </TD>
    </TR>
</table>

