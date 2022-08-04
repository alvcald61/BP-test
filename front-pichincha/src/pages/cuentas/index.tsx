import React, { useEffect, useState } from "react";
import { getBankAccounts, deleteBankAccount } from "../../services/BankAccount";
import { BankAccount } from "../../types/types";
import Table from "../../components/Table";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

type Props = {};

const keys = [
  "clientName",
  "accountNumber",
  "accountType",
  "initialBalance",
  "currentBalance",
  "isActive",
];

const columns = [
  "Nombre del Cliente",
  "N° Cuenta",
  "Tipo de Cuenta",
  "Saldo Inicial",
  "Saldo Actual",
  "Estado",
  "Acciones",
];

const Cuentas = (props: Props) => {
  const [bankAccounts, setBankAccounts] = useState<BankAccount[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    const getResult = async () => {
      const data = await getBankAccounts();
      setBankAccounts(data.data);
    };
    getResult();
  }, []);

  const onDelete = (e: React.MouseEvent<HTMLButtonElement>)=>{
    const deleteAccountAction = async ()=>{
      const id = e.currentTarget.value;
      const response = await deleteBankAccount(+id);

      if(response.status === "OK"){
        const newClientes = bankAccounts.filter(bankAccounts=>bankAccounts.id !== +id);
        setBankAccounts(newClientes);
        swal("Cliente eliminado", "El cliente ha sido eliminado", "success");
      }
      else{
        swal("Error", "No se pudo eliminar el cliente", "error");
      }
    }
    deleteAccountAction();
  }

  const onUpdate = (e: React.MouseEvent<HTMLButtonElement>)=>{
      const id = e.currentTarget.value;
      navigate(`/cuentas/editar/${id}`);    
  }

  return (
    <>
      <Table
        id="id"
        title="Cuentas Bancarias"
        data={bankAccounts}
        keys={keys}
        searchField={"clientName"}
        columns={columns}
        buttonLink="/cuentas/nuevo"
        deleteAction={onDelete}
        updateAction={onUpdate}
      ></Table>
    </>
  );
};

export default Cuentas;
