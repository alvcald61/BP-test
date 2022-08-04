import React from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Clientes from './pages/clientes';
import NuevoCliente from './pages/clientes/newClient';
import Cuentas from './pages/cuentas';
import Movimientos from './pages/movimientos';
import Reportes from './pages/reportes';
import NuevoMovimiento from './pages/movimientos/newMovement';
import NuevaCuenta from './pages/cuentas/newBankAccount';
function App() {
  return (
    <div className="light-mode"> 
      <Navbar />
      <section className='title'>
        <img src={`${process.env.PUBLIC_URL}/descarga.png`} alt="" width="200" height="50"/>    
      </section>
      <hr></hr>
      <section className="page-content">
        <section className="search-and-user" ></section>
        <Routes>
          <Route path="/" element={<Clientes />} />
          <Route path="clientes" element={<Clientes />} >
          </Route>
            <Route path="/clientes/nuevo" element={<NuevoCliente />} />  
            <Route path="/clientes/editar/:id" element={<NuevoCliente />} />  
          <Route path="cuentas"  element={<Cuentas />} />
          <Route path="/cuentas/nuevo"  element={<NuevaCuenta />} />
          <Route path="/cuentas/editar/:id"  element={<NuevaCuenta />} />
          <Route path="movimientos"  element = {<Movimientos/>}/>
          <Route path="/movimientos/nuevo"  element = {<NuevoMovimiento/>}/>
          <Route path="/movimientos/editar/:id"  element = {<NuevoMovimiento/>}/>
          <Route path="reportes"  element ={<Reportes/>}/>
          <Route path="*" element={<div>404</div>} />
        </Routes>
      </section>
    </div>
  );
}

export default App;
