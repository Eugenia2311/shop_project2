<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 10.06.2017
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>petshop</title>
</head>
<body>
<c:set var="count" value="${0}" />
<div align="right">
    <a href="<c:url value="/shop">
                <c:param name="action" value="viewCart" />
                </c:url>"> Корзина</a>
    <c:if test="${!(empty cart)}">
        <c:set var="count" value="${0}" />
        <c:forEach items="${cart}" var="entry">
            <c:set var="count" value="${count+entry.value}"/>
        </c:forEach>
        (${count})
    </c:if>
    <c:choose>
        <c:when test="${user==null}">
            <a href="<c:url value="/login">
                   <c:param name="action" value="login" />
                </c:url>">Войти</a>
        </c:when>
        <c:otherwise>
            Привет, ${user.login}!&ensp;
            <a href="<c:url value="/login">
                <c:param name="logout" value="logout" />
                </c:url>">Выйти</a>
        </c:otherwise>
    </c:choose>
</div>
  <h2>Интернет магазин товаров для животных</h2>

    <h3>${petCategory.name}(${productCategory.name})</h3>
     <table>
     <c:forEach items="${products}" var="entry">
         <tr>
             <td>${entry.key.name}</td>
             <td>${entry.key.price} руб &ensp;</td>
             <td>
         <c:choose>
                 <c:when test="${entry.value>0}">
         <a href="<c:url value="/shop">
                <c:param name="action" value="addToCart" />
                <c:param name="productId" value="${entry.key.id}" />
                </c:url>">Добавить в корзину</a><br/>
         </c:when>
             <c:otherwise>
                 Нет в наличии
             </c:otherwise>
         </c:choose>
             </td>
         </tr>
     </c:forEach>
</table><br/><br/>

  <div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewMainPage" />
                </c:url>">Вернуться на главную страницу</a>
  </div>
</body>
</html>
