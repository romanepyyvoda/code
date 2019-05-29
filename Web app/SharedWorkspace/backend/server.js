var express = require('express');

var port = 5002;



var app = express();



app.use('/', require('./api')());



app.listen(port, function () {

    console.log('Listening on port ' + port + '!');

});