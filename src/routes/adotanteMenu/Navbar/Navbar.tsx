import { AppBar, Toolbar, IconButton,Typography, Stack, ThemeProvider } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { Logout } from '../../../Components/Logout/logout';

const theme = createTheme({
    palette: {
      primary: {
        main: '#f9a870',
        contrastText: '#ffff',
      },
      secondary: {
        main: '#42a5f5',
        contrastText: '#ffff',
      }
    },
  });

// função para deslogar do sistema e remover as informações armazenadas no session storage
const handleClick = () => {
    // Remover as informações do session storage
    sessionStorage.setItem("isLoggedIn", "false");
    sessionStorage.setItem("isLogged", "false");

    // Redirecionar para a página de login ou para outra página adequada após o logout
    window.location.href = "/";
};

export const Navbar = () =>{
    return(
        <AppBar position='relative' style={{ background: 'transparent' }}>
            <Toolbar>
                <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
                    <IconButton size='large' edge='start' color='inherit' aria-label='Início'>
                        <img src="./imgs/logos/LogoSemFonte_Azul.svg" style={{ width: 60 }}></img>
                    </IconButton>
                </Typography>
                <Stack direction='row' spacing={1}>
                    <ThemeProvider theme={theme}>
                        <Link to="/queroAdotar" style={{ textDecoration: 'none' }}>
                            <Button variant="contained" color="primary"> Quero Adotar </Button> 
                        </Link>
                        {/* <Button variant="contained" color="secondary"> Formulário responsável </Button>  */}
                        <Button variant="contained" color="secondary"> ONGS e Protetores </Button> 
                    </ThemeProvider>
                </Stack>
                <Logout></Logout>
            </Toolbar>
        </AppBar>
    )
}