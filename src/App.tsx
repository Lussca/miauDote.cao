//imports padrão react
import './App.css';
import { useEffect } from 'react';

//imports de rotas
import { Outlet, useNavigate } from 'react-router-dom';
import { BrowserRouter as Router, Route, useLocation } from 'react-router-dom';
import { userService } from './service/userService';
function App(this: any) {

  const location = useLocation();

  const navigate = useNavigate();

  useEffect(() => {
    userService.verificationUserLogged(true, navigate);
  },[])

  useEffect(() => {
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