var isLoggedIn = sessionStorage.getItem("isLoggedIn");
var isLogged = sessionStorage.getItem("isLogged");

const path = window.location.pathname;
const segments = path.split('/');
const lastSegment = segments[segments.length - 1];

function verificacaoLogin(){
    if (isLoggedIn == "true" && isLogged == "true" && lastSegment != "") {
        return;
    } else {
        window.location.href = "/";
    }
}

export default verificacaoLogin;