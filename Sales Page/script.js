const buttons = document.querySelectorAll('.check');
for (let i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('click', function () {
        document.querySelector('.modalWindow').style.display = 'block';
    });
}

const remove = document.querySelectorAll('.closing');
for (let i = 0; i < remove.length; i++) {
    remove[i].addEventListener('click', function () {
        document.querySelector('.modalWindow').style.display = 'none';
    });
}
window.onclick = function (event) {
    if (event.target == document.querySelector('.modalWindow')) {
        document.querySelector('.modalWindow').style.display = "none";
    }
}
