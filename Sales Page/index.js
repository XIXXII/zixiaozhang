const fs = require("fs");
const http = require("http"); //package that require when you try to create HTTP server
const url = require("url"); 
const querystring = require('querystring');
//post method is different from get method. In get method, we will able to read the user input from
//url, but will not able to read data through "POST" method
//so wee need add this package called "querystring" to decode the user input
const { Pool} = require('pg')

const pool = new Pool({
    user:'postgres',
    host:'Localhost',
    database:'rollsroyce',
    password:'1289',
    port:5434
}); //make connection to database (postgres)
const port = 3000;
const server = http.createServer(); //create the server
server.on("listening", listen_handler); 
server.listen(port); //listen to port 3000
function listen_handler(){
    console.log(`Now Listening On Port ${port}`);
}
server.on("request", request_handler);//triiger the callback function by event "request"

function request_handler(req, res){
    if(req.url === "/"){ //if the client open the localhost:3000 will get the page of checkout 
        const form = fs.createReadStream("checkout.html");
        res.writeHead(200, {"Content-Type": "text/html"});
        form.pipe(res);
    }
    else if(req.url.startsWith("/checkout")){ //after the customer click on the sumbit button
        //it will perform the action checkout, this will add to our url and then, the new request occur
        var data = '';  //this is empty string, and will add data to it (user input)
        req.on('data', function(chunk){
            data+=chunk;
        }); 
        req.on('end',function(){ //once the data completely receive, call function
            //to decode the user input into string 
            data = decodeURI(data); 
            var userInput = querystring.parse(data);
            addInfo(userInput,res);//call the function to customer information and made new transaction
        })
    }
    else{
        //if the custoemr or client search any other url, teh client will get 404 NOT FOUND message and 
        res.writeHead(404, {"Content-Type": "text/html"});
        res.end(`<h1>404 Not Found</h1>`);
    }
}
function addInfo(userInput,res){
    const first = userInput.firstName;
    const last = userInput.lastName;
    const car = userInput.model;
    const quan = parseInt(userInput.quantity);

    let memId;
    let productId;
    let transId;
    let price;
    let total;
    let result; 
    let remain;
    let newStock;
    //as we get the customer input, first we will check the stock of the product 
    //so first we need to get the information about this specific product on the database
    //use pool.query to do the query on database and then assign each value to productId, price, remains for later use
    pool.query(`SELECT * from products WHERE model_name like '${car}'`,(err,res)=>{
        productId = res.rows[0].product_id;
        price = res.rows[0].price;
        remain = res.rows[0].stock;
        checkStock();
    });
//this function will check the stock of the product
//we use the if else to check the stock
    function checkStock(){
        //if there has not enough stock or out of stock, customer will redirect to the page with "come back later"
        if((remain === 0) || (remain-quan<0)){
            result = `<html><head><title>Thank-You</title><style>body{background: #020117;
                cursor: url("https://img.icons8.com/small/20/ffffff/circled--v1.png"), cell;
                font-family: 'Roboto', sans-serif;color: #f1f1e8;height:100vh; text-align: center;}
              .box{width:100vh;height:20vh;margin-left: auto;margin-right: auto;position: relative;
                  background:#020117;border-radius: 25px;margin-top: 10vh;}
              .box::before {content: '';position: absolute;top: 0;left: 0;right: 0;
                  bottom: 0;background: linear-gradient(45deg, #ffd800, #ff5520, #750cf2, #0cbcf2);
                  z-index: -2;filter: blur(50px);border-radius: 20px;}
                </style></head>
                <body><div class = "box"><br>
                <h1>Sorry, only ${remain} left for this model. Order another model or come back later!</h1>
                </div></body></html>`;
            res.write(result); //transimit the data to the client 
            res.end(); //close the connection
        }
        //if there have enough stock, we will check if the customer is a member of us 
        else{
            newStock = remain-quan; 
            checkCustomer();
        }
    }
    //this function also use if else
    function checkCustomer(){
        //if the customer click on "No", we will first insert the customer informatio to table customers and get his/her member_id that just creat for later use
        if(userInput.customer === 'No'){
            var queryString =  `insert into customers(first_name, last_name,phone_number, email, address,city, state, zip_code) values('${first}','${last}','${userInput.phoneNumber}','${userInput.emailAddress}','${userInput.address}','${userInput.city}','${userInput.state}','${userInput.zipCode}')`;
            pool.query(queryString, (err,res)=>{
                console.log(err,res); //print to the terminal 
                readMember(); //call function to get member id
            });
            function readMember(){
                pool.query(`SELECT * from customers WHERE first_name = '${first}' and last_Name = '${last}'`,(err,res)=>{
                    memId = res.rows[0].member_id;
                    reduceStock();
                });
            }
        }
        //if the customer click on "Yes", we will made transaction for him/her, and update the table for products
        //since the customer purchase our product, we need to decrease the value of stock
        else{
            pool.query(`SELECT * from customers WHERE first_name = '${first}' and last_Name = '${last}'`,(err,res)=>{
                memId = res.rows[0].member_id;
                reduceStock();
            });
        }
    }
    //so we just do an update to the table product with new value of the stock, then made transaction processing 
    function reduceStock(){
        pool.query(`update products set stock=${newStock} WHERE product_id = ${productId} `,(err,res)=>{
            console.log(err);
            madeTransaction();
        });
    }
    //in this function we will create insert to the transaction table to record who made this purchase, when, which product and how much thet spend
    //so the value we have to insert is come from the user inpur and member_id, product_id from previous steps
    //also the new value for total amount, it is multiply by price(from database) and quantity(from user)
    function madeTransaction(){
        total=price*quan;
        var queryString =  `insert into transaction(member_id, product_id, dateOfPurchase, quantity, amount, creditCard, cardExpire) values('${memId}','${productId}','${userInput.today}','${quan}','${total}','${userInput.cardNumber}','${userInput.expire}')`;
        pool.query(queryString, (err,res)=>{
            console.log(err,res);
            readTransaction();
        });
        function readTransaction(){
            pool.query(`SELECT * from transaction WHERE member_id = ${memId} and product_id = ${productId} ORDER BY transaction_id DESC`,(err,res)=>{
                transId = res.rows[0].transaction_id;
                recordOrder();
            });
        }
    }
    //after we made transaction processing, we also store the order number and customer id to the table call orderHistory 
    function recordOrder(){
        var queryString =  `insert into orderHistory(member_id, transaction_id) values('${memId}','${transId}')`;
        pool.query(queryString, (err,res)=>{
            console.log(err,res);
            printInfo()
        })
    }
    //after all, we need to send the response to client, to let customer know he/she has successfully made purchase
    //and this page will print out the information of customer_id, order numebr, and total amount they spend on this order
    function printInfo(){
        result = `<html><head><title>Thank-You</title><style>body{background: #020117;
                  cursor: url("https://img.icons8.com/small/20/ffffff/circled--v1.png"), cell;
                  font-family: 'Roboto', sans-serif;color: #f1f1e8;height:100vh; text-align: center;}
                .box{width:100vh;height:55vh;margin-left: auto;margin-right: auto;position: relative;
                    background:#020117;border-radius: 25px;margin-top: 10vh;}
                .box::before {content: '';position: absolute;top: 0;left: 0;right: 0;
                    bottom: 0;background: linear-gradient(45deg, #ffd800, #ff5520, #750cf2, #0cbcf2);
                    z-index: -2;filter: blur(50px);border-radius: 20px;}
            </style></head>
        <body><div class = "box"><br>
                <h1><img style="vertical-align: middle;"src="https://img.icons8.com/ios-glyphs/90/ffffff/thank-you.png"/>THANKS FOR YOUR ORDER</h1>
                <p>Member Id: ${memId}</p>
                <p>Order Number: ${transId}</p>
                <p>Order Total: ${total} </p>
            </div>
        </body>
    </html>`;
        res.write(result); //transmit data to the client / customer
        res.end();//close the connection 
    }

}
