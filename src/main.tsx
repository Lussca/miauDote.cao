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
import QueroAdotar from './routes/QueroAdotar/QueroAdotar'
import Candidaturas from './routes/Candidaturas/Candidaturas'

const router = createBrowserRouter([
  {
    path: "/login",
    element: <Login />
  },
  {
    path: "/registrar",
    element: <Registro />
  },
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "",
        element: <AdotanteMenu />
      },
      {
        path: "ongMenu",
        element: <OngMenu />
      },
      {
        path: "adotanteMenu",
        element: <AdotanteMenu />
      },
      {
        path: "queroAdotar",
        element: <QueroAdotar />
      },
      {
        path: "candidaturas",
        element: <Candidaturas />
      },
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
