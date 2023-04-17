import './App.css';
import React from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

// imports paginas
import Login from './Login/Login';

function App() {

  return (
    <div className='App'>
      <Login/>
    </div>
  );
  
}

export default App