var KEY_A = 65;
var KEY_D = 68;
var KEY_W = 87;

var KEY_SPACE = 32;

var KEY_LEFT = 37;
var KEY_UP = 38;
var KEY_RIGHT = 39;


var keysDown = {};

//noinspection SpellCheckingInspection
addEventListener("keydown", function (e) {
    if (e.keyCode == KEY_SPACE) {
        spaceKeyDown();
    } else {
        keysDown[e.keyCode] = true;
    }
}, false);

//noinspection SpellCheckingInspection
addEventListener("keyup", function (e) {
    keysDown[e.keyCode] = false;
}, false);
