const fs = require("fs");
const http = require("http");
const https = require("https");
const url = require("url");

const credentials = require("./auth/credentials.json");

const port = 3000;
const server = http.createServer(); //create the server

server.on("listening", listen_handler);
server.listen(port); //listen to port 3000
function listen_handler(){
    console.log(`Now Listening On Port ${port}`);
}

server.on("request", request_handler);

function request_handler(req, res){
    console.log(`New Request from ${req.socket.remoteAddress} for ${req.url}`);
    if(req.url === "/"){
        const form = fs.createReadStream("html/index.html");
        res.writeHead(200, {"Content-Type": "text/html"});
        form.pipe(res);
    }
    else if(req.url.startsWith("/words")){
        const words = url.parse(req.url,true).query; //returns "{word: hello}
        const user_input = words.word; //I only want the information provide by the client, exactly the word that the client type in 
        if(user_input.length === 0){ //not type in any input, response end
            res.writeHead(404, {"Content-Type": "text/html"});
            res.end(`<h1>Connect Again</h1>`); //closing connection
        }
        search_words(user_input, res);//contacting two APIs 
    }
    //any other endpoint will serve a 404 NOT Found 
    else{
        res.writeHead(404, {"Content-Type": "text/html"});
        res.end(`<h1>404 Not Found</h1>`);
    }
}
let done; 
function search_words(user_input, res){
    done = {tasks_completed: 0};
    const words_endpoint = `https://wordsapiv1.p.rapidapi.com/words/${user_input}`;
    https.request(
        words_endpoint, 
        {method:"GET", headers:credentials},
        (words_stream) => process_stream(words_stream, parse_words, user_input, res)
    ).end();
}

function process_stream (stream, callback , ...args){
	let body = "";
	stream.on("data", chunk => body += chunk);
	stream.on("end", () => callback(body,done, ...args));
}

function parse_words(body, done,user_input, res){
    let words_object = JSON.parse(body);
    request_giphy(user_input, res); //receiving the data from first API, now send the second API request 
    let wordsearchs = words_object && words_object.results;
    let result;
    //if the user_input has no result in the "Words"
    if(words_object.success === false || wordsearchs === undefined){
        result = `<div style = "width:50% ; float: left;"><h1>Words Result:</h1><ul>${result}</ul></div>`;
    }
    else{
        result = wordsearchs.map(format_search).join('');
        result = `<div style = "width:50% ; float: left;"><h1>Words Result:</h1><ul>${result}</ul></div>`;
    }
    res.write(result, () => terminate(done, res)); //write the result, and call the function terminate
    function format_search (search) {
        let search_result = search && search.definition;
        let part = search && search.partOfSpeech;
        return `<p>Definition: ${search_result}</p><p>PartOfSpeech: ${part}<p>`;
    }
}

function request_giphy(user_input, res){
    const giphy_endpoint = `https://api.giphy.com/v1/gifs/search?api_key=ZaPg2zdXC3PaVUS8ASTrjcYB9hDpCfJQ&q=${user_input}`;
    https.request(
        giphy_endpoint, 
        {method:"GET"},
        (words_stream) => process_stream(words_stream, parse_giphy, res)
    ).end();
}
function parse_giphy(body, done, res){
    let gifs = JSON.parse(body);
    let images;
    if(gifs.data.length === 0){
        images = `<h1>Giphy:Word Not found</h1>`;
        images = `<div style="width:50%; float: right;">${images}</div>`;
    }
    else{
        images = deliver_image(gifs);
        images = `<div style="width:50%; float: right;">${images}</div>`;
    }
    res.write(images, () => terminate(done, res));
    function deliver_image(search){
        return `<img src = ${search.data[0].images.original.url}>`;
    }
}

function terminate(done, res){
    done.tasks_completed++; 
    if(done.tasks_completed === 2){  //get both response from APIs, the response for this query should be end 
        res.end(); 
    }
}