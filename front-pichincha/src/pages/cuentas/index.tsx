import React, { useEffect, useState } from "react";
import { getBankAccounts } from "../../services/BankAccount";
import { BankAccount } from "../../types/types";
import Table from "../../components/Table";

type Props = {}

const keys = ["clientName", "accountNumber", "accountType", "initialBalance", "currentBalance", "isActive"];

const columns= ["Nombre del Cliente", "N° Cuenta", "Tipo de Cuenta", "Saldo Inicial", "Saldo Actual", "Estado"]
 

const Cuentas = (props: Props) => {

  const [bankAccounts, setBankAccounts] = useState<BankAccount[]>([]);

  useEffect(() => {
    const getResult = async () => {
      const data = await getBankAccounts();
      setBankAccounts(data.data);
    };
    getResult();
  }, []);

  return (
    <>
      <Table title="Cuentas Bancarias" data={bankAccounts} keys={keys} searchField = {"clientName"} columns={columns} buttonLink="/cuentas/nuevo"></Table>
    </>
  );
  
}

export default Cuentas