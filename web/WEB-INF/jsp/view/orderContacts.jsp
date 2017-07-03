<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 22.06.2017
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>PetShop</title>
</head>
<body>
<c:set var="customerFName" value="${''}" />
<c:set var="customerLName" value="${''}" />
<c:set var="customerAddress" value="${''}" />
<c:if test="${user!=null}">
    <c:if test="${user.fName!=null}">
        <c:set var="customerFName" value="${user.fName}" />
    </c:if>
    <c:if test="${user.lName!=null}">
        <c:set var="customerLName" value="${user.lName}" />
    </c:if>
    <c:if test="${user.address!=null}">
        <c:set var="customerAddress" value="${user.address}" />
    </c:if>
</c:if>
<form method="POST" action="<c:url value="/shop" />">
    <input type="hidden" name ="action" value="addContacts"/>
    <c:if test="${user==null}">
        Email:<br/>
        <input type="text" required name="email"/><br /><br />
    </c:if>
    Имя:<br />
    <input type="text" required name="fName" value = "${customerFName}"/><br /><br />
    Фамилия:<br />
    <input type="text" required name="lName" value = "${customerLName}"/><br /><br />
    Адрес доставки:<br />
    <input type="text" required name="address" value = "${customerAddress}" /><br /><br />
    <input type="submit" value="Продолжить" />
</form>


<div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewCart" />
                </c:url>">Вернуться в корзину</a></div>
</body>
</html>
