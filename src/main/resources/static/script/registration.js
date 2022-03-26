const registration = 'http://localhost:8080/auth/registration'
const login = 'http://localhost:8080/auth/login'

$(document).ready(function () {
    $('#btn').click(function (e) {
        e.preventDefault()
        sendAjaxRegForm('registration_form', registration)
    })

})

function sendAjaxRegForm(ajax_form, url) {
    $.ajax({
        url: url,
        type: 'POST',
        data: $('#'+ajax_form).serialize(),
        success: (data, textStatus, xhr) => checkResultRegistration(textStatus),
        error: (data, textStatus, xhr) => checkResultRegistration(textStatus)
    })
}

function checkResultRegistration(textStatus) {
    console.log(textStatus)
    if (textStatus == 'success') window.location.href = login
    else window.location.href = registration
}