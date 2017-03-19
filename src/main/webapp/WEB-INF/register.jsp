<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/include/header.jsp" %>
<%@ include file="/WEB-INF/include/navbar_main.jsp" %>

<c:url value="/register" var="registerURL"/>
<c:url value="/login" var="loginURL"/>

<div class="container ">
    <div class="card card-container">
        <h4> Create new account</h4>
        <!-- <form action="${registerURL}" method="post" class="form-signin">
            <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required>
            <input name="firstName" type="text" id="inputFirstName" class="form-control" placeholder="First name" required>
            <input name="lastName" type="text" id="inputLastName" class="form-control" placeholder="Last name" required>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <input type="password" id="inputRepeatedPassword" class="form-control" placeholder="Repeat password" required>

            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Create</button>
        </form> -->
        
        <form action="${registerURL}" method="post" id="register-form">
            <div class ="form-group ">
            <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="email" type="text" class="form-control" placeholder="Email address" >
                
            </div>

            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="firstName" type="text"  class="form-control" placeholder="First name" >
            </div>

            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="lastName" type="text"  class="form-control" placeholder="Last name" >
            </div>

            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="password" type="password" id="passwordControl" class="form-control" placeholder="Password" >
            </div>

            
            <div class ="form-group">
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <input name="passwordRepeat" type="password"  class="form-control" placeholder="Repeat password" >
            </div>
            <div class="form-group col-md-12">
                <div class="checkbox">
                    <label>  
                        <input id="terms" name="terms" type="checkbox"> 
                            <!-- EN -->
                            I have read, consent and agree to Company's User Agreement and Privacy Policy. I understand that I can change my communication preferences at any
                            time. 
                    </label>
                </div>
            </div>

            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Create</button>
        </form>


        <div class="margin-bottom-10 small">
        	<em>
            Don't remember password? <a href="${passwordForget}" class="register">Password reminder</a>
            </em>
        </div>
        <div class="margin-bottom-10">
            <a href="${loginURL}" class="forgot-password">Back to login</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/include/footer.jsp" %>