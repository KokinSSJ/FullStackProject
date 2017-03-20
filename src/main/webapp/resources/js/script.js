$(function () {

        // Dominik KokinSSJ
    $.validator.setDefaults({
        errorClass: 'help-block', // for error labels -> bootstrap error labels // override
        highlight: function(element){ // for error -> add error + remove favicon
            $(element)
                .closest('.form-group')
                .addClass('has-error')
                .removeClass('has-feedback');
        },
        unhighlight: function(element){ //when currect -> remove error class + add succes class... 
            // + add favicon tick for
            $(element)
                .closest('.form-group')
                .removeClass('has-error')
                .addClass('has-success has-feedback ');
        },
        errorPlacement: function(error, element){ // when checkbox insert class for parrent!
            if(element.prop('type')==='checkbox'){
                error.insertAfter(element.parent());
            } else{
                error.insertAfter(element);
            }
        }
    });

    var  strong = 3; //password strong 

    $.validator.addMethod('strongPassword', function (value, element) {
        return this.optional(element) // check if this element is required or not, don't do unnecessary things
        || value.length>=strong     // how strong password must be
        && /\d/.test(value)         //at least one digit
        && /[a-z]/i.test(value);        //at list one char

            //for false return
    }, 'Your password has to have at least ' + strong + ' chars and consist at least one digit and one char');

    // validate form register form
    $("#register-form").validate({
        rules: {
            email:{
                required: true,
                email: true
            },
            password: {
                required: true,
                strongPassword: true
            },
            passwordRepeat: {
                required: true,
                equalTo: "#passwordControl"
            },
            firstName:{
                required: true,
                nowhitespace: true,
                lettersonly: true
            },
            lastName:{
                required: true,
                nowhitespace: true
            },
            terms: {
                required: true
            }
        },
        messages:{
            email:{
                required: 'Enter your Email',
                email: 'Email must consist \'@\' char'

            },
            password: {
                required: 'Enter your password',
            },
            passwordRepeat:{
                required: 'Enter your password',
                equalTo: 'Password and repeat password are not the same'
            },
            firstName: {
                required: 'Enter your name',
                nowhitespace: 'Enter your name - without white spaces',
                lettersonly: 'Only chars'
            },
            lastName: {
                required: 'Enter your last name',
                nowhitespace: 'Without white spaces, allowed \"-\"',
                lettersonly: 'Only chars'
            },
            terms:{
                required: 'You have to agree with terms!'
            }
        }
    });
});