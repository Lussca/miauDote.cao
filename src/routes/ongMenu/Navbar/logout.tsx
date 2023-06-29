import { Typography} from '@mui/material';
import { Button } from '@mui/material';

// função para deslogar do sistema e remover as informações armazenadas no session storage
const handleClick = () => {
    // Seta as informações do session storage
    sessionStorage.setItem("isLogged", "false");
    sessionStorage.setItem('isOng', 'false');

    // Remover as informações do session storage
    sessionStorage.removeItem('userId');

    // Redirecionar para a página de login ou para outra página adequada após o logout
    window.location.href = "/login";
};

export const Logout = () =>{
    return(
        <Typography variant='h6' component='div' sx={{ ml: 10 }}>
            <Button id="signIn" variant="contained" color="error" onClick={handleClick}> sair </Button> 
        </Typography>
    )
}