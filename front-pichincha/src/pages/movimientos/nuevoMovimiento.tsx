import React, {useEffect, useState} from 'react'
import { postMovement } from '../../services/Movements';
import { getClientes } from '../../services/Clients';
import { getClientBakAccounts } from '../../services/BankAccount';
import {Movement, Client, BankAccount} from '../../types/types'
import moment from 'moment';

type Props = {}

const NuevoMovimiento = (props: Props) => {
    const [status, setStatus] = useState<string | undefined>(undefined);
    const [clients, setClients] = useState<Client[]>([]);
    const [bankAccounts, setBankAccounts] = useState<BankAccount[]>([]);
    const [movement, setMovement] = useState<Movement>({} as Movement);

    useEffect(() => {
        const getResult = async () => {
            const data = await getClientes();
            setClients(data.data);
          };
          getResult();
    },[]);

    const getMovements = async () => {
      if(!movement.clientId){
        return;
      }
        const data = await getClientBakAccounts(movement.clientId);
        setBankAccounts(data.data);
      };

    useEffect(() => {
      getMovements();
    },[movement.clientId]);

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
        return;
      }
      let data = e.target.value;
      if(e.target.name === 'transactionDate'){
       let value = e.target.value;
       data = moment(value).format('DD/MM/YYYY');
      }
      setMovement({...movement, [e.target.name]: data});
    }
  
    const onChangeDropdown = (e: React.ChangeEvent<HTMLSelectElement>) => {
      if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
        return;
      }
      setMovement({...movement, [e.target.name]: e.target.value});
    }
  
    const submitForm = (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      setStatus("Loading...");
      const sendData = async ()=>{
        const response = await postMovement(movement);
        setStatus(response.status === "OK" ? "Se ha guardado exitosamente" : response.error.message);
        getMovements();
      }
      sendData();

      setTimeout(() => {
        setStatus(undefined);
      }, 10000);
  
    }


  
    return (
      <div>
          <h2 style={{ marginBottom: "20px", fontSize: "3rem" }}>
          <strong>Añadir Cuentas Bancarias</strong>
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
                <label style={{margin: "10px"}}>Cliente: </label>
                <select onChange={onChangeDropdown} name="clientId" id="movementName" style={{margin: "10px", width: "50%", height: "30px"}}>
                <option value="" disabled selected hidden>Nombre - DNI</option>
                    {
                        clients.map((client: Client) => {
                        return <option key={client.id} value={client.id}>{ `${client.name} - ${client.identifier}` }</option>
                        })
                    }
                </select>
              </div>
              <div>
                <label style={{margin: "10px"}}>Cuenta Bancaria: </label>
                <select onChange={onChangeDropdown} name="bankAccountId" id="bankAccountId" style={{margin: "10px", width: "50%", height: "30px"}}>
                <option value="" disabled selected hidden>Numero de cuenta - Saldo disponible</option>
                    
                    {
                        bankAccounts.map((acc: BankAccount) => {
                        return <option key={acc.id} value={acc.id}>{ `${acc.accountNumber} - ${acc.currentBalance}` }</option>
                        })
                    }
                </select>
              </div>
              <div>
 
                <label style={{margin: "10px", cursor: "default"}}>Monto de la transacción: </label>
                <input onChange={onChange} type="number" id="amount" name="amount"  style={{margin: "10px", width: "50%", height: "30px"}}/>
              </div>
              <div>
                <label style={{margin: "10px", cursor: "default"}}>Fecha de Transacción: </label>
                <input onChange={onChange} type="date" id="transactionDate" name="transactionDate" 
                 style={{margin: "10px", width: "50%", height: "30px"}}/>
              </div>
              <div>
                <label style={{margin: "10px", cursor: "default"}}>Tipo de Transacción: </label>
                <select onChange={onChangeDropdown} name="movementType" id="movementType" style={{margin: "10px", width: "50%", height: "30px"}}>
                  <option value="" disabled selected hidden>Retiro o Depósito</option>
                  <option value="WITHDRAWAL"> Retiro</option>
                  <option value="DEPOSIT"> Depósito</option>
                </select>
              </div>
              <div>
                {status && <label style={{margin: "10px", cursor: "default"}}>{status}  </label>}
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

export default NuevoMovimiento