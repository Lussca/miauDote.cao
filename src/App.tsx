import './App.css';
import React from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

import { Outlet } from 'react-router-dom';

function App() {

  return (
    <div className='App'>
      <Outlet />
    </div>
  );
  
}

export default App