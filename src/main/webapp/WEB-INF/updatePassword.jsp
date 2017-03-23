<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/include/header.jsp" %>
<%@ include file="/WEB-INF/include/navbar_main.jsp" %>


<c:url value="/register" var="registerURL"/>
<c:url value="/password-forget" var="passwordForget"/>

<c:url value="/user/updatePassword" var="updatePassword"/>

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
        <h4> Change your password ${user.firstName}</h4>
        <form action="${updatePassword}" method="post" id="changepassword-form" class="register">
           

            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="password" type="password" id="passwordControl" class="form-control" placeholder="Password" >
            </div>

            
            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="passwordRepeat" type="password"  class="form-control" placeholder="Repeat password" >
            </div>

            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Change Password</button>
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