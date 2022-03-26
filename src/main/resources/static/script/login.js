const login = 'http://localhost:8080/auth/login'
const successUrl = 'http://localhost:8080/chat'

$(document).ready(function () {
    $('#btn').click(function (e) {
        e.preventDefault()
        sendAjaxRegForm('login_form', login)
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
    if (textStatus == 'success') window.location.href = successUrl
    else window.location.href = login
}