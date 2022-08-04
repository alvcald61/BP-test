import React, {useEffect, useState} from 'react'
import { postBankAccount, searchBankAccount } from '../../services/BankAccount';
import { getClientes } from '../../services/Clients';
import {BankAccount, Client} from '../../types/types'
import {useParams} from 'react-router-dom'
type Props = {}

const NuevaCuenta = (props: Props) => {
    const [status, setStatus] = useState<string | undefined>(undefined);
    const [clients, setClients] = useState<Client[]>([]);
    const [bankAccount, setBankAccount] = useState<BankAccount>({} as BankAccount);
    const {id} = useParams();
    useEffect(() => {
        const getResult = async () => {
            const data = await getClientes();
            setClients(data.data);
            if(id){
                const data = await searchBankAccount(id);
                setBankAccount(data.data);
            }
          };
          getResult();
    },[]);


    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
        return;
      }
      setBankAccount({...bankAccount, [e.target.name]: e.target.value});
    }
  
    const onChangeDropdown = (e: React.ChangeEvent<HTMLSelectElement>) => {
      if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
        return;
      }
      setBankAccount({...bankAccount, [e.target.name]: e.target.value});
    }
  
    const submitForm = (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      setStatus("Loading...");
      const sendData = async ()=>{
        let response ;
        if(!id){
           response = await postBankAccount(bankAccount);
        }
        else {
          response = await searchBankAccount(id);
        }
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
            <strong>{!id?"Añadir Movimientos" : "Editar Movimiento"}</strong>
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
                  <select onChange={onChangeDropdown} name="clientId" id="bankAccountName" value={clients.find(client => client.name ===bankAccount.clientName)?.id} style={{margin: "10px", width: "50%", height: "30px"}}>
                      <option value="" disabled selected hidden>Clientes</option>
                      {
                          clients.map((client: Client) => {
                          return <option key={client.id} value={client.id}>{ `${client.name} - ${client.identifier}` }</option>
                          })
                      }
                  </select>
                </div>
                <div>
                  <label style={{margin: "10px"}}>Numero de cuenta: </label>
                  <input onChange={onChange} type="number" id="accountNumber" name="accountNumber" value={bankAccount.accountNumber} style={{margin: "10px", width: "50%", height: "30px"}}/>
                </div>
                <div>
                  <label style={{margin: "10px"}}>Saldo Inicial: </label>
                  <input onChange={onChange} type="number" id="initialBalance" name="initialBalance" value={bankAccount.initialBalance} style={{margin: "10px", width: "50%", height: "30px"}}/>
                </div>
                <div>
                  <label style={{margin: "10px"}}>Tipo de Cuenta: </label>
                  <select onChange={onChangeDropdown} name="accountType" id="accountType" value={bankAccount.accountType === "Cuenta de Ahorros"? "SAVINGS_ACCOUNT":"CURRENT_ACCOUNT" } style={{margin: "10px", width: "50%", height: "30px"}}>
                    <option value="" disabled selected hidden>Corriente o de Ahorros</option>
                    <option value="CURRENT_ACCOUNT"> Cuenta Corriente</option>
                    <option value="SAVINGS_ACCOUNT"> Cuenta de Ahorros</option>
                  </select>
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

export default NuevaCuenta