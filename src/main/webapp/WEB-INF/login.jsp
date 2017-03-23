<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/include/header.jsp" %>
<%@ include file="/WEB-INF/include/navbar_main.jsp" %>

<c:url value="/login" var="loginURL"/>
<c:url value="/register" var="registerURL"/>
<c:url value="/password-forget" var="passwordForget"/>



<div class="container">
    <c:if test="${param.logout != null}">
        <div class="alert alert-success fade in">
            <a class="close" data-dismiss="alert" href="#">&times;</a>
            <p>You've logged out</p>
        </div>
    </c:if>
    <c:if test="${param.register != null}">
        <div class="alert alert-info fade in">
            <a class="close" data-dismiss="alert" href="#">&times;</a>
            <p>Register successful. You can log in</p>
        </div>
    </c:if>
    <c:if test="${param.error != null}">
        <div class="alert alert-danger fade in">
            <a class="close" data-dismiss="alert" href="#">&times;</a>
            <p>Username or password is incorrect</p>
        </div>
    </c:if>
       <c:if test="${ server_info_status == 'ok'}">
        <div class="alert alert-success fade in">
            <a class="close" data-dismiss="alert" href="#">&times;</a>
            <p>Server: ${server_info}</p>
        </div>
    </c:if>
    <c:if test="${ server_info_status == 'error'}">
        <div class="alert alert-danger fade in">
            <a class="close" data-dismiss="alert" href="#">&times;</a>
            <p>Server: ${server_info}</p>
        </div>
    </c:if>
    
    <div class="card card-container">
        <h4> Login to your account</h4>
        <form action="${loginURL}" method="post" class="form-signin">
     
            <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address"  required  autofocus>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required >
            <div id="remember" class="checkbox">
                <label>
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
        </form>
        <div class="margin-bottom-10 small">
       		<em>
            Don't have account? <a href="${registerURL}" class="register">Create account</a>
            </em>
        </div>
        <div class="margin-bottom-10 small">
        	<em>
            Don't remember password? <a href="${passwordForget}" class="register">Password reminder</a>
            </em>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/include/footer.jsp" %>