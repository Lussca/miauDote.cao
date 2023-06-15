import { NavigateFunction } from "react-router-dom";

export const userService = {
    verificationUserLogged(isToBeLogged:boolean, navigate:NavigateFunction) {
        const isLogged = sessionStorage.getItem("isLogged")
        const userOng = sessionStorage.getItem("isOng")
    
        if(isLogged === 'true' && !isToBeLogged){
            if(userOng === 'true'){
                navigate("/ongMenu")
            } else{
                navigate("/adotanteMenu")
            }
        }

        if(isLogged === 'false' && isToBeLogged){
            navigate("/login")
        }
    }
}