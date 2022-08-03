import React from 'react'
import {Link} from 'react-router-dom' 


type Props = {}

const Navbar = (props: Props) => {
  return (
    <header className="page-header">
  <nav>
    <ul className="admin-menu">
      <li>
        <Link to="/clientes">
          <svg>
            <use href="#clientes"></use>
          </svg>
          <span>Clientes</span>
        </Link>
      </li>
      <li>
        <Link to="/cuentas">
          <svg>
            <use href="#cuentas"></use>
          </svg>
          <span>Cuentas</span>
        </Link>
      </li>
      <li>
        <Link to="/movimientos">
          <svg>
            <use href="#movimientos"></use>
          </svg>
          <span>Movimientos</span>
        </Link>
      </li>
      <li>
        <Link to="/reportes">
          <svg>
            <use href="#reportes"></use>
          </svg>
          <span>Reportes</span>
        </Link>
      </li>
    </ul>
  </nav>
</header>
  )
}

export default Navbar