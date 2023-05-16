//imports padrão react
import React, { Children } from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'

//imports de rotas
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

//Rotas pags
import Login from './routes/Login/Login'
import Registro from './routes/Registro/Registro'
import ErrorPage from './routes/ErrorPage/ErrorPage'

const router = createBrowserRouter([
  {
    path: "/login",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/login",
        element: <Login />
      },
      {
        path: "registrar",
        element: <Registro />
      },
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
