import React, { useEffect, useState } from "react";
import { getMovements, deleteMovement } from "../../services/Movements";
import { BankAccount } from "../../types/types";
import Table from "../../components/Table";
import {useNavigate} from "react-router-dom";
import swal from "sweetalert";
type Props = {};

const keys = [
  "clientName",
  "accountNumber",
  "movementType",
  "amount",
  "availableBalance",
  "transactionDate",
];

const columns = [
  "Nombre del Cliente",
  "N° Cuenta",
  "Tipo de Transacción",
  "Cantidad",
  "Saldo Disponible",
  "Fecha de Transacción",
  "Acciones"
];

const Movimientos = (props: Props) => {
  const navigate = useNavigate();
  const [movements, setMovements] = useState<BankAccount[]>([]);

  useEffect(() => {
    const getResult = async () => {
      const data = await getMovements();
      console.log(data.data);
      setMovements(data.data);
    };
    getResult();
  }, []);



  const onDelete = (e: React.MouseEvent<HTMLButtonElement>)=>{
    const deleteMovementAction = async ()=>{
      const id = e.currentTarget.value;
      const response = await deleteMovement(+id);

      if(response.status === "OK"){
        const newClientes = movements.filter(movement=>movement.id !== +id);
        setMovements(newClientes);
        swal("Cliente eliminado", "El cliente ha sido eliminado", "success");
      }
      else{
        swal("Error", "No se pudo eliminar el cliente", "error");
      }
    }
    deleteMovementAction();
  }

  const onUpdate = (e: React.MouseEvent<HTMLButtonElement>)=>{
      const id = e.currentTarget.value;
      navigate(`/movimientos/editar/${id}`);    
  }

  return (
    <>
      <Table
        id="id"
        title={"Movimientos Bancarios"}
        data={movements}
        keys={keys}
        searchField={"clientName"}
        columns={columns}
        buttonLink="/movimientos/nuevo"
        deleteAction={onDelete}
        updateAction={onUpdate}
      ></Table>
    </>
  );
};

export default Movimientos;
