<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 15.06.2017
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>PetShop</title>
</head>
<body>
<h2>Регистрация</h2>

<c:choose>
    <c:when test="${!(isRegistered)}">
        Заполните поля для регистрации<br />
          <c:if test="${registrationFailed}">
               <b>Пользователь с таким логином уже существует. Попробуйте еще раз.</b><br /><br />
          </c:if>
          <form method="POST" action="<c:url value="/login" />">
           <input type="hidden" name ="action" value="register"/>
              Login<br />
           <input type="text" required name="userLogin" /><br /><br />
              Password<br />
            <input type="password" required name="password" /><br /><br />
              Email<br/>
            <input type="text" required name="email" /><br /><br />
            <input type="submit" value="Зарегистрироваться" />
           </form>
    </c:when>
    <c:otherwise>
        <div align="center">
            <h3>Поздравляем, Вы успешно зарегистрирваны!</h3>
        </div>
    </c:otherwise>
</c:choose>

<div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewMainPage" />
                </c:url>">Вернуться на главную страницу</a>
</div>
</body>
</html>
