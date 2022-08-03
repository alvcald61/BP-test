import React, {useState} from 'react'
import { postClient } from '../../services/Clients';
import {Client} from '../../types/types'
type Props = {}

const NuevoCliente = (props: Props) => {
  const [status, setStatus] = useState<string | undefined>(undefined);
  const [client, setClient] = useState<Client>({} as Client);
  
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
      return;
    }
    setClient({...client, [e.target.name]: e.target.value});
  }

  const onChangeDropdown = (e: React.ChangeEvent<HTMLSelectElement>) => {
    if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
      return;
    }
    setClient({...client, [e.target.name]: e.target.value});
  }

  const submitForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setStatus("Loading...");
    const sendData = async ()=>{
      const response = await postClient(client);
      setStatus(response.status === "OK" ? "Se ha guardado exitosamente" : "Ha ocurrido un error");
    }
    sendData();
    setTimeout(() => {
      setStatus(undefined);
    }, 2000);

  }

  return (
    <div>
        <h2 style={{ marginBottom: "20px", fontSize: "3rem" }}>
        <strong>Añadir Clientes</strong>
      </h2>
      <section
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          padding: "20px",
          border: "1px solid #F5E51A",
        }}
        >
          <form style={{width: "100%", height: "100%"}} onSubmit={submitForm}>
            <div>
              <label style={{margin: "10px"}}>Nombre: </label>
              <input onChange={onChange} type="text" id="name" name="name"  style={{margin: "10px", width: "50%", height: "30px"}}/>
            </div>
            <div>
              <label style={{margin: "10px"}}>Genero: </label>
              <select onChange={onChangeDropdown} name="gender" id="gender" style={{margin: "10px", width: "50%", height: "30px"}}>
                <option value="M"> Masculino</option>
                <option value="F"> Femenino</option>
              </select>
            </div>
            <div>
              <label style={{margin: "10px"}}>Edad: </label>
              <input onChange={onChange} type="number" id="age" name="age"  style={{margin: "10px", width: "50%", height: "30px"}}/>
            </div>
            <div>
              <label style={{margin: "10px"}}>DNI: </label>
              <input onChange={onChange} type="number" id="identifier" name="identifier"  style={{margin: "10px", width: "50%", height: "30px"}}/>
            </div>
            <div>
              <label style={{margin: "10px"}}>Dirección: </label>
              <input onChange={onChange} type="text" id="address" name="address"  style={{margin: "10px", width: "50%", height: "30px"}}/>
            </div>
            <div>
              <label style={{margin: "10px"}}>Numero telefónico: </label>
              <input onChange={onChange} type="number" id="phoneNumber" name="phoneNumber"  style={{margin: "10px", width: "50%", height: "30px"}}/>
            </div> 
            <div>
              {status && <label style={{margin: "10px"}}>{status}  </label>}
            </div>

            <input type="submit" value="Submit"  style={{
              backgroundColor: "#F5E51A",
              borderRadius: "5px",
              color: "#334FFF",
              fontSize: "1.5rem",
              border: "none",
              padding: "10px",
              marginTop: "20px",
              width: "100%",
              
              right: "0",
            }}/>
           </form>
          
        
        </section>
    </div>


  )
}

export default NuevoCliente