import { Typography} from '@mui/material';
import { Button } from '@mui/material';

// função para deslogar do sistema e remover as informações armazenadas no session storage
const handleClick = () => {
    // Remover as informações do session storage
    sessionStorage.setItem("isLoggedIn", "false");
    sessionStorage.setItem("isLogged", "false");

    // Redirecionar para a página de login ou para outra página adequada após o logout
    window.location.href = "/";
};

export const Logout = () =>{
    return(
        <Typography variant='h6' component='div' sx={{ ml: 10 }}>
            <Button id="signIn" variant="contained" color="error" onClick={handleClick}> sair </Button> 
        </Typography>
    )
}