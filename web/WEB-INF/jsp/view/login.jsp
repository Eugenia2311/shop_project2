<%--
  Created by IntelliJ IDEA.
  User: Eugenia
  Date: 15.06.2017

--%>

<!DOCTYPE html>
<html>
<head>
    <title>Petshop</title>
</head>
<body>
<h2>Авторизация</h2>
Введите логин и пароль для авторизации<br /><br />
<c:if test="${loginFailed}">
    <b>Некорректные логин и пароль. Попробуйте еще раз.</b><br /><br />
</c:if>
<form method="POST" action="<c:url value="/login" />">
    <input type="hidden" name ="action" value="login"/>
    Login<br />
    <input type="text" required name="userLogin" /><br /><br />
    Password<br />
    <input type="password" required name="password" /><br /><br />
    <input type="submit" value="Войти" />
</form>
Новый пользователь?&ensp;<a href="<c:url value="/login">
                <c:param name="action" value="register" />
                </c:url>">Зарегистрироваться</a><br/>

<div align="center"><a href="<c:url value="/shop">
                <c:param name="action" value="viewMainPage" />
                </c:url>">Вернуться на главную страницу</a>
</div>
</body>
</html>