const mainUrl = 'http://localhost:8080'

const chatFormUrl = mainUrl + '/chat'
const addFriendUrl = mainUrl + '/friends'
const whoIamUrl = mainUrl + '/my_name'
const findUserUrl = mainUrl + "/exist/"

let sendTo;
let sendFrom;
let stompClient;



function addFriend(friendUsername) {
    let socket = new SockJS(addFriendUrl)
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        stompClient.subsribe("/")
    })
}

function connectToChat(username) {
    let socket = new SockJS(chatFormUrl)
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/messages/" + username, function (response) {
            console.log(response)
            let data = JSON.parse(response.body)
            if (data['status'] == 'OK') {
                addMessageToScreen(data['message'])
            }
        })
    })
}

function addMessageToScreen(message) {
    let div = document.createElement('div');
    $(div).addClass('message')
        .html(message)
        .appendTo($('#chat_field'))
}

function sendMessage(from, text, to) {
    stompClient.send("/app/chat/" + from + "/" + to, {}, JSON.stringify({
        message: text,
        from: from,
        to: to,
        status: 'DEFAULT'
    }))
}

$(document).ready(function () {

    $.ajax({
        url: whoIamUrl,
        type: 'GET',
        success: (data, textStatus, xhr) => {
            checkResultWhoIam(data)
            stompClient.subscribe("/topic/messages/" + data, function (response) {
                console.log(response)
                let text = JSON.parse(response.body)
                if (text['status'] === 'OK') {
                    addMessageToScreen(data['message'])
                }
            })
        },
        error: (data, textStatus, xhr) => checkResultWhoIam(textStatus)
    })

    $('#sendMessage').click(function (e) {
        e.preventDefault()
        let message = $('.rounded-input').val()
        sendMessage(sendFrom, message, sendTo)
    })

    $('#findFriend').click(function (e) {
        e.preventDefault()
        sendTo = $('.friend-input').val()
        $.ajax({
            url: findUserUrl + sendTo,
            type: 'GET',
            success: (data, textStatus, xhr) => {
                if (data == "TRUE") { connectToChat(sendTo) }
                else if (data == "FALSE") alert('Such user does not exist')
            },
            error: (data, textStatus, xhr) => {}
        })
    })
})

function sendAjaxRegForm(ajax_form, url) {
    let username = $('#username').val()
    $.ajax({
        url: url,
        type: 'POST',
        data: $('#'+ajax_form).serialize(),
        success: (data, textStatus, xhr) => checkResultRegistration(textStatus),
        error: (data, textStatus, xhr) => checkResultRegistration(textStatus)
    })
}

function checkResultWhoIam(textStatus) {
    sendFrom = textStatus
}