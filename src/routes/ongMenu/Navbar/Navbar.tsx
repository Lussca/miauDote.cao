import { useState } from 'react';

import { Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { createTheme } from '@mui/material/styles';
import { AppBar, Toolbar, IconButton,Typography, Stack, ThemeProvider } from '@mui/material';

import { Logout } from './logout';
import AnimalModalAdd from '../DialogAnimlByOng/DialogAnimalByOng';

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

  const [openModal, setOpenModal] = useState(false);

    const openModalRegister = () => {
        setOpenModal(true);
    }
    
    return(
        <AppBar position='relative' style={{ background: 'transparent' }}>
            <Toolbar>
                <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
                    <Link to="/ongMenu">
                        <IconButton size='large' edge='start' color='inherit' aria-label='Início'>
                            <img src="./imgs/logos/LogoSemFonte_Azul.svg" style={{ width: 60 }}></img>
                        </IconButton>
                    </Link>
                </Typography>
                <Stack direction='row' spacing={1}>
                    <ThemeProvider theme={theme}>
                        <Button variant="contained" color="primary" onClick={() => openModalRegister()}> adicionar animal </Button>
                    </ThemeProvider>
                </Stack>
                <Logout></Logout>
            </Toolbar>
            <AnimalModalAdd
                open={openModal}
                onClose={() => setOpenModal(false)}
            />
        </AppBar>
    )
}