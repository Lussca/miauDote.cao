//imports padrão react
import './App.css';
import React from 'react';
import { useState } from 'react';

//imports de rotas
import { Outlet } from 'react-router-dom';

function App() {

  return (
    <div className='App'>
      <Outlet />
    </div>
  );
  
}

export default App