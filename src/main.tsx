//imports padrão react
import React, { Children } from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'

//imports de rotas
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

//Rotas pags
import Login from './routes/Login/Login'
import Registro from './routes/Registro/Registro'
import OngMenu from './routes/ongMenu/ongMenu'
import AdotanteMenu from './routes/adotanteMenu/adotanteMenu'
import ErrorPage from './routes/ErrorPage/ErrorPage'

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Login />
      },
      {
        path: "registrar",
        element: <Registro />
      },
      {
        path: "ongMenu",
        element: <OngMenu />
      },
      {
        path: "adotanteMenu",
        element: <AdotanteMenu />
      },
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
