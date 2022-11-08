const express = require('express');
const bodyParser = require('body-parser');

var app = express();

const http = require('http');
const server = http.createServer(app);

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.get('/', (req, res) => {
    res.send('<h1>Hello world</h1>');
  });

server.listen(4000,()=>{
    console.log('Server is running on port number 4000')
})

// console.log('Log PORT ===> '+String.PORT);
const socketio = require('socket.io')(server)
//Chat Server
var io = socketio.listen(server);
io.on('connection', function(socket) {

    //The moment one of your client connected to socket.io server it will obtain socket id
    //Let's print this out.
    console.log(`Connection : SocketId = ${socket.id}`)
    //Since we are going to use userName through whole socket connection, Let's make it global.   
    var userName = '';

    // Encode Base64 Image as user profile picture
    var userProfileImage = '';
    
    socket.on('subscribe', function(data) {

        console.log('subscribe triggered')

        const room_data = JSON.parse(data)
        userName = room_data.userName;
        const roomName = room_data.roomName;

        /***
         * new data to include using base 64
         */
        userProfileImage = room_data.userProfileImage;

        socket.join(`${roomName}`)
        console.log(`Profile Image: ${userProfileImage}`)
        console.log(`Username : ${userName} joined Room Name : ${roomName}`)
        
       
        // Let the other user get notification that user got into the room;
        // It can be use to indicate that person has read the messages. (Like turns "unread" into "read")

        //TODO: need to chose
        //io.to : User who has joined can get a event;
        //socket.broadcast.to : all the users except the user who has joined will get the message
        // socket.broadcast.to(`${roomName}`).emit('newUserToChatRoom',userName);
        io.to(`${roomName}`).emit('newUserToChatRoom',userName);

    })

    socket.on('unsubscribe',function(data) {
        console.log('unsubscribe trigged')
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    

        console.log(`Username : ${userName} leaved Room Name : ${roomName}`)
        socket.broadcast.to(`${roomName}`).emit('userLeftChatRoom',userName)
        socket.leave(`${roomName}`)
    })

    socket.on('newMessage',function(data) {
        console.log('newMessage triggered')


        const messageData = data
        const messageContent = messageData.messageContent
        const roomName = messageData.roomName


        /**
         * fetching the message in the frontend side as 2 meaning chats from this value came from another user
         */
        const messageType = '2'

        console.log(`[Room Number ${roomName}] ${userName} : ${messageContent}`)
        
        // Just pass the data that has been passed from the writer socket
        const chatData = {
            /**
             * Passing the existing profile data such as;
             * userProfileImage and userName
             */
            userProfileImage : userProfileImage,
            userName : userName,
            messageContent : messageContent,
            roomName : roomName,
            messageType : messageType
        }

        socket.broadcast.to(`${roomName}`).emit('updateChat',JSON.stringify(chatData)) // Need to be parsed into Kotlin object in Kotlin
    })

    //If you want to add typing function you can make it like this.
    
    // socket.on('typing',function(roomNumber){ //Only roomNumber is needed here
    //     console.log('typing triggered')
    //     socket.broadcast.to(`${roomNumber}`).emit('typing')
    // })

    // socket.on('stopTyping',function(roomNumber){ //Only roomNumber is needed here
    //     console.log('stopTyping triggered')
    //     socket.broadcast.to(`${roomNumber}`).emit('stopTyping')
    // })

    socket.on('disconnect', function () {
        console.log("One of sockets disconnected from our server.")
    });
})