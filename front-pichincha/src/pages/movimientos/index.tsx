import React, { useEffect, useState } from "react";
import { getMovements } from "../../services/Movements";
import { BankAccount } from "../../types/types";
import Table from "../../components/Table";

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
];

const Movimientos = (props: Props) => {
  const [movements, setMovements] = useState<BankAccount[]>([]);

  useEffect(() => {
    const getResult = async () => {
      const data = await getMovements();
      console.log(data.data);
      setMovements(data.data);
    };
    getResult();
  }, []);

  return (
    <>
      <Table
        title={"Movimientos Bancarios"}
        data={movements}
        keys={keys}
        searchField={"clientName"}
        columns={columns}
        buttonLink="/movimientos/nuevo"
      ></Table>
    </>
  );
};

export default Movimientos;
