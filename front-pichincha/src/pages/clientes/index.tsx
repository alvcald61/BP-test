import React, { useEffect, useState } from "react";
import { getClientes } from "../../services/Clients";
import { Client } from "../../types/types";
import Table from "../../components/Table";
type Props = {};

const columns = ["Nombre", "genero", "Edad", "DNI", "Dirección", "Teléfono"];

const keys = ["name", "gender", "age", "identifier", "address", "phoneNumber"];

const Clientes = (props: Props) => {
  const [clientes, setClientes] = useState<Client[]>([]);

  useEffect(() => {
    const getResult = async () => {
      const data = await getClientes();
      setClientes(data.data);
    };
    getResult();
  }, []);

  return (
    <>
      <Table
        title="Clientes"
        data={clientes}
        keys={keys}
        searchField={"name"}
        columns={columns}
        buttonLink="/clientes/nuevo"
      ></Table>
    </>
  );
};

export default Clientes;
