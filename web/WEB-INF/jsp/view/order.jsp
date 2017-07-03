<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 26.06.2017
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Petshop</title>
</head>
<body>
<c:choose>
    <c:when test="${order!=null}">
        <h2 align="center">Спасибо за то что выбрали наш магазин!</h2><br/>
        <h4>Ваш заказ:</h4><br/>
        <c:set var="cost" value="${0}" />
        <table>
            <tr>
                <th>Найменование товара</th>
                <th>Количество</th>

            </tr>
            <c:forEach items="${productsInOrder}" var="productInOrder">
                <tr>
                    <td>${productInOrder.product.name}</td>
                    <td>${productInOrder.quantity}</td>

                </tr>
                <c:set var="cost" value="${cost+productInOrder.product.price*productInOrder.quantity}"/>
            </c:forEach>
        </table>

        <h4 align="center">
            Сумма заказа: ${cost}
        </h4>
        <h4>Статус заказа: ${order.status}</h4><br/>
        ${order.status}

        <div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewMainPage" />
                </c:url>">Вернуться на главную страницу</a>
        </div>
    </c:when>
    <c:otherwise>
     <h2 align="center">Проверьте данные заказа.</h2><br/>
      <h3>Заказ:</h3>

            <c:set var="cost" value="${0}" />
            <table>
               <tr>
                   <th>Найменование товара</th>
                   <th>Количество</th>
                   <th>Цена за единицу</th>
               </tr>
             <c:forEach items="${cart}" var="entry">
               <tr>
                 <td>${entry.key.name}</td>
                 <td>${entry.value}</td>
                 <td>${entry.key.price} руб</td>
               </tr>
              <c:set var="cost" value="${cost+entry.key.price*entry.value}"/>
             </c:forEach>
            </table>

           <h4 align="center">
              Итоговая сумма: ${cost}
           </h4>
         <h3>Контактные данные:</h3><br/>
            Имя:<br/>
            <c:out value="${fName}"/><br/>
            Фамилия:<br/>
        <c:out value="${lName}"/><br/>
            Адрес:<br/>
        <c:out value="${address}"/><br/>

           <div align="right"><a href="<c:url value="/shop">
                 <c:param name="action" value="takeOrder" />
                    </c:url>">Подтвердить заказ</a><br/></div>

           <div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewCart" />
                </c:url>">Вернуться в корзину</a></div>
    </c:otherwise>
</c:choose>
</body>
</html>
