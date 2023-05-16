//imports padrão react
import './App.css';
import React, { useEffect } from 'react';
import { useState } from 'react';

//imports de rotas
import { Outlet } from 'react-router-dom';
import { BrowserRouter as Router, Route, useLocation } from 'react-router-dom';

function App() {

  const location = useLocation();

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