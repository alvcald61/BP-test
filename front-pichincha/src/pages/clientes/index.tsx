import React, { useEffect, useState } from "react";
import { getClientes, deleteClient, putClient } from "../../services/Clients";
import { Client } from "../../types/types";
import Table from "../../components/Table";
import swal from "sweetalert";
import { useNavigate } from "react-router-dom";


type Props = {};

const columns = ["Nombre", "genero", "Edad", "DNI", "Dirección", "Teléfono", "Acciones"];

const keys = ["name", "gender", "age", "identifier", "address", "phoneNumber"];

const Clientes = (props: Props) => {
  const navigate = useNavigate();
  const [clientes, setClientes] = useState<Client[]>([]);

  useEffect(() => {
    const getResult = async () => {
      const data = await getClientes();
      setClientes(data.data);
    };
    getResult();
  }, []);

  const onDelete = (e: React.MouseEvent<HTMLButtonElement>)=>{
    const deleteClientAction = async ()=>{
      const id = e.currentTarget.value;
      const response = await deleteClient(+id);

      if(response.status === "OK"){
        const newClientes = clientes.filter(client=>client.id !== +id);
        setClientes(newClientes);
        swal("Cliente eliminado", "El cliente ha sido eliminado", "success");
      }
      else{
        swal("Error", "No se pudo eliminar el cliente", "error");
      }
    }
    deleteClientAction();
  }

  const onUpdate = (e: React.MouseEvent<HTMLButtonElement>)=>{
      const id = e.currentTarget.value;
      navigate(`/clientes/editar/${id}`);    
  }

  return (
    <>
      <Table
        id={"id"}
        title="Clientes"
        data={clientes}
        keys={keys}
        searchField={"name"}
        columns={columns}
        buttonLink="/clientes/nuevo"
        deleteAction={onDelete}
        updateAction={onUpdate}
      ></Table>
    </>
  );
};

export default Clientes;
