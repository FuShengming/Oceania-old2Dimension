let show_num = [];

window.onload = function () {
    this.draw()
};

function draw() {
    let canvas_width = $("#canvas")[0].clientWidth;
    let canvas_height = $("#canvas")[0].clientHeight;
    let canvas = $("#canvas")[0];
    let context = canvas.getContext("2d");
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    let sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0,q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m";
    let aCode = sCode.split(",");
    let aLength = aCode.length;//获取到数组的长度

    for (let i = 0; i <= 3; i++) {
        let j = Math.floor(Math.random() * aLength);
        let deg = Math.random() * 30 * Math.PI / 180;
        let txt = aCode[j];
        show_num[i] = txt;
        let x = 3 + i * 12;
        let y = 15 + Math.random() * 8;
        context.font = "18px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (let i = 0; i <= 3; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
    }
    for (let i = 0; i <= 15; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        let x = Math.random() * canvas_width;
        let y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}

function randomColor() {
    let r = Math.floor(Math.random() * 256);
    let g = Math.floor(Math.random() * 256);
    let b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

function clearNotice() {
    $("#captcha-warn").removeClass("captcha-error").addClass("hidden-error").text("")
}


