var express = require('express');
var bodyParser     =        require("body-parser");
var app = express();
var multer = require('multer');
var upload = multer({dest: 'uploads/'});
var http = require('http').Server(app);

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post('/post', upload.single("picture"), function (req, res) {
	console.log(req.body.top);
    console.log("request");
	res.send({
	"hey":"ha",
	"hey":"ha"
		});

//	res.sendfile("/home/amine/Dev/cmc.im/SmileyEmotion/app/src/main/res/drawable/tree.jpg");
});

app.get('/', function(req,res){
	res.send("hey");
});

http.listen(8081,"192.168.1.20");
