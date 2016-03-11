addMessageToConsole = (msg) =>
  con = $('#console')
  con.append(msg + '\n')
  con.scrollTop(con[0].scrollHeight)

onMessage = (event) ->
  console.log('onMessage')
  console.log(event)
  addMessageToConsole(event.data)

onOpen = (event) ->
  console.log('onOpen')
  console.log(event)
  addMessageToConsole('Connected!')

onClose = (event) ->
  console.log('onClose')
  console.log(event)
  addMessageToConsole("Bye :(")

onError = (event) ->
  console.log('onError')
  console.log(event)
  addMessageToConsole("Something nasty happened, sorry :(")

openWSConnection = (endpoint) ->
  addMessageToConsole('Trying to connect...')
  webSocket = new WebSocket(endpoint)
  webSocket.onmessage = onMessage
  webSocket.onopen = onOpen
  webSocket.onerror = onError

$('#console').append('RESTful Web Service Console started...')

root = exports ? this

root.connect = openWSConnection