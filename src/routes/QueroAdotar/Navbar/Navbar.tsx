import { AppBar, Toolbar, IconButton,Typography, Stack, ThemeProvider } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import PetsIcon from '@mui/icons-material/Pets';
import { Button } from '@mui/material';

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
    sessionStorage.removeItem("isLoggedIn");

    // Redirecionar para a página de login ou para outra página adequada após o logout
    window.location.href = "/";
};

export const Navbar = () =>{
    return(
        <AppBar position='fixed' style={{ background: 'transparent' }}>
            <Toolbar>
                <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
                    <IconButton size='large' edge='start' color='inherit' aria-label='Início'>
                        <img src="./imgs/logos/LogoSemFonte_Azul.svg" style={{ width: 60 }}></img>
                    </IconButton>
                </Typography>
                <Stack direction='row' spacing={1}>
                    <ThemeProvider theme={theme}>
                        <Button variant="contained" color="primary"> Quero Adotar </Button> 
                        <Button variant="contained" color="secondary"> Formulário responsável </Button> 
                        <Button variant="contained" color="primary"> ONGS e Protetores </Button> 
                    </ThemeProvider>
                </Stack>
                <Typography variant='h6' component='div' sx={{ ml: 10 }}>
                    <Button id="signIn" variant="contained" color="error" onClick={handleClick}> sair </Button> 
                </Typography>
            </Toolbar>
        </AppBar>
    )
}