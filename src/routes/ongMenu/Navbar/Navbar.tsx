import { AppBar, Toolbar, IconButton,Typography, Stack, ThemeProvider } from '@mui/material';
import { createTheme } from '@mui/material/styles';
import { Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { Logout } from './logout';

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

export const Navbar = () =>{
    return(
        <AppBar position='relative' style={{ background: 'transparent' }}>
            <Toolbar>
                <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
                    <Link to="/adotanteMenu">
                        <IconButton size='large' edge='start' color='inherit' aria-label='Início'>
                            <img src="./imgs/logos/LogoSemFonte_Azul.svg" style={{ width: 60 }}></img>
                        </IconButton>
                    </Link>
                </Typography>
                <Stack direction='row' spacing={1}>
                    <ThemeProvider theme={theme}>
                        <Link to="/queroAdotar" style={{ textDecoration: 'none' }}>
                            <Button variant="contained" color="primary"> adicionar animal </Button> 
                        </Link>
                    </ThemeProvider>
                </Stack>
                <Logout></Logout>
            </Toolbar>
        </AppBar>
    )
}