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

  <h1>Корзина:</h1>
  <c:choose>
      <c:when test="${(cart==null)or(empty cart)}">
         В корзине нет товаров<br/><br/><br/>
      </c:when>

      <c:otherwise>
          <c:set var="cost" value="${0}" />
          <table>
              <tr>
                  <th>Найменование товара</th>
                  <th>Количество</th>
                  <th>Цена за единицу</th>
              </tr>
          <c:forEach items="${cart}" var="entry">
              <form method="GET" action="<c:url value="/shop" />">
                  <tr>
                      <input type="hidden" name="action" value="updateCart"/>
                      <input type="hidden" name="productId" value="${entry.key.id}">
                      <td>${entry.key.name}</td>
                      <td><input type="number" min="1" max="10" required name="count"
                                 value="${entry.value}"/>
                          <input type="submit" value="Сохранить"/>
                      </td>
                      <td>${entry.key.price} руб</td>
                      <td>
                          <a href="<c:url value="/shop">
                <c:param name="action" value="removeItemFromCart" />
                <c:param name="productId" value="${entry.key.id}" />
                </c:url>">Удалить</a>
                      </td>
                  </tr>
              </form>
              <c:set var="cost" value="${cost+entry.key.price*entry.value}"/>
          </c:forEach>
          </table>
          <c:if test="${updateFailed}">
              Необходимого количества товара нет в наличии.
          </c:if>
          <h4 align="center">
              Итоговая сумма: ${cost}
          </h4>
          <h4 align="right">
              <a href="<c:url value="/shop">
                <c:param name="action" value="emptyCart" />
                </c:url>">Очистить корзину</a>
          </h4>
          <h3 align="right">
              <a href="<c:url value="/shop">
                <c:param name="action" value="addContacts" />
                </c:url>">Заказать</a>
          </h3>
      </c:otherwise>
  </c:choose>

  <div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewMainPage" />
                </c:url>">Вернуться на главную страницу</a>
  </div>
</body>
</html>
