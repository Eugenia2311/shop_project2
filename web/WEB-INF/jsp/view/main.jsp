<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 10.06.2017
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Petshop</title>
</head>
<body>
  <div align="right">
      <a href="<c:url value="/shop">
                <c:param name="action" value="viewCart" />
                </c:url>">Корзина</a>
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

  <c:forEach items="${petCategories}" var="category">
      <h3>${category.name}</h3>
      <c:forEach items="${category.productCategories}" var="subCategory">
          <a href="<c:url value="/shop">
                        <c:param name="action" value="viewProductSubCategory" />
                        <c:param name="petCategoryId" value="${category.id}" />
                        <c:param name="productCategoryId" value="${subCategory.id}" />

                    </c:url>">${subCategory.name}</a><br/>
      </c:forEach>

  </c:forEach>

</body>
</html>
