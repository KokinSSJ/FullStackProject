$(function () {

    $.validator.setDefaults({
        errorClass: 'help-block', // for error labels -> bootstrap error labels
        highlight: function(element){
            $(element)
                .closest('.form-group')
                .addClass('has-error')
                .removeClass('has-feedback');
        },
        unhighlight: function(element){
            $(element)
                .closest('.form-group')
                .removeClass('has-error')
                .addClass('has-success has-feedback ');
        },
        errorPlacement: function(error, element){
            if(element.prop('type')==='checkbox'){
                error.insertAfter(element.parent());
            } else{
                error.insertAfter(element);
            }
        }
    });

    var  strong = 3;

    $.validator.addMethod('strongPassword', function (value, element) {
        return this.optional(element) // check if this element is required or not, don't do unnecessary things
        || value.length>=strong     // how strong password must be
        && /\d/.test(value)         //at least one digit
        && /[a-z]/i.test(value);        //at list one char

            //for false return
    }, 'Twoje hasło musi mieć przynajmniej ' + strong + ' znaków oraz zawierać przynajmniej jedną cyfrę i jeden znak');

    // validate form register 
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
                required: 'Proszę podać email',
                email: 'Uwzględnij znak \'@\' '

            },
            password: {
                required: 'Proszę podać hasło',
            },
            passwordRepeat:{
                required: 'Proszę podać poprawne hasło',
                equalTo: 'Nie sa takie same'
            },
            firstName: {
                required: 'Podaj swoje imię',
                nowhitespace: 'Podaj tylko imię - brak spacji',
                lettersonly: 'Tylko litery'
            },
            lastName: {
                required: 'Podaj swoje nazwisko',
                nowhitespace: 'Bez spacji, możliwa \"-\"',
                lettersonly: 'Tylko litery'
            },
            terms:{
                required: 'Musisz się zgodzić na warunki !'
            }
        }
    });
    // body...
});