//imports padrão react
import './App.css';
import { useEffect } from 'react';
import verificacaoLogin from './verification/Authentication';

//imports de rotas
import { Outlet } from 'react-router-dom';
import { BrowserRouter as Router, Route, useLocation } from 'react-router-dom';

function App(this: any) {

  const location = useLocation();

  useEffect(() => {
    // verificacaoLogin();

    const body = document.querySelector('body');
    if (body) {
      if (location.pathname === '/adotanteMenu') {
        body.classList.add('bodyAdotanteMenu');
      } else {
        body.classList.remove('bodyAdotanteMenu');
      }
    }
  }, [location]);

  return (
    <div className='App'>
      <Outlet />
    </div>
  );
  
}

export default App