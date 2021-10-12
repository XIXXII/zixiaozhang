
let randomNumber = Math.floor(Math.random() * 100 + 1);
console.log(`The secret number is ${randomNumber}`);
let totalScore = 10;
let higherScore = 0;
document.getElementById("playAgain").addEventListener("click", function(){
    randomNumber = Math.floor(Math.random() * 100 + 1);
    console.log(randomNumber);
    document.body.style.backgroundColor = "#8DB596";
    document.getElementById("outputScore").innerHTML = higherScore;
    totalScore = 10;
    document.getElementById("current").textContent = `Chance left: ${totalScore}`;
    document.getElementById("totalScore").innerHTML = `Let's start a new round....`;
    document.getElementById('bar').value = 100;
    document.getElementById("image").style.backgroundImage = "url('image/think.jpg')";
    document.getElementById("image").innerHTML = ``;
    document.getElementById("next").onclick = getValue;
});

function checkScore(score){
    if(score > higherScore){
        higherScore = score;
    }
    else{
        higherScore = higherScore;
    }
    document.getElementById("outputScore").innerHTML = higherScore;
}
function getValue() {
    let userInput = document.getElementById('number').value;
    document.getElementById("totalScore").innerHTML = totalScore;
    if (isNaN(userInput)) {
        document.getElementById("totalScore").innerHTML = `Invalid input.`;
    }
    else if(userInput.length == 0){
        document.getElementById("totalScore").innerHTML = `No number`;
    }
    else if (randomNumber == userInput) {
        document.getElementById("totalScore").innerHTML = `Congraduations! Your score is: ${totalScore}`
        checkScore(totalScore);
        document.body.style.backgroundColor = "#6f9eaf";
        document.getElementById("current").textContent = `Chance left: ${totalScore}`;
        document.getElementById("image").style.backgroundImage = "url('https://media.giphy.com/media/TFNbcscr9JUUigDzrZ/giphy.gif')";
        document.getElementById("image").innerHTML = randomNumber;
        document.getElementById("next").onclick = "";
    }
    else if(userInput > 100 || userInput < 1){
        document.getElementById("totalScore").innerHTML = `Please guessing the number between 1 ~ 100`;
        document.getElementById("current").textContent = `Chance left: ${totalScore}`;
    }
    else if (randomNumber > userInput) {
        totalScore--;
        document.getElementById('bar').value -= 10;
        if(totalScore<=0){
            document.getElementById("totalScore").innerHTML = `You lost the game`;
            document.body.style.backgroundColor = "#ee9595";
            document.getElementById("image").style.backgroundImage = "url('https://media.giphy.com/media/1jARfPtdz7eE0/giphy.gif')";
            document.getElementById("next").onclick = "";
        }
        else{
            document.getElementById("totalScore").innerHTML = `HINT... Lower than secret number.`;
        }
        document.getElementById("current").textContent = `Chance left: ${totalScore}`;
    }
    else {
        totalScore--;
        document.getElementById('bar').value -= 10;
        if(totalScore<=0){
            document.getElementById("totalScore").innerHTML = `You lost the game`;
            document.body.style.backgroundColor = "#ee9595";
            document.getElementById("image").style.backgroundImage = "url('https://media.giphy.com/media/1jARfPtdz7eE0/giphy.gif')";
            document.getElementById("next").onclick = "";
        }
        else{
            document.getElementById("totalScore").innerHTML = `HINT... Higher than secret number. `;
        }
        document.getElementById("current").textContent = `Chance left: ${totalScore}`;
    }
}

