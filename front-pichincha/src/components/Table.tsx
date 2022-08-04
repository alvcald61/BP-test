import React, {  useState } from "react";
import {useNavigate} from "react-router-dom";

type Props = {
    data?: any[],
    keys?: string[],
    columns?: string[],
    searchField: string,
    buttonLink: string,
    title? : string
    deleteAction: (e: React.MouseEvent<HTMLButtonElement>) => void
    updateAction: (e: React.MouseEvent<HTMLButtonElement>) => void
    id: string
}

const Table = ({ data, keys, columns, searchField, buttonLink, title, deleteAction, updateAction, id} : Props) => {
  const [buscador, setBuscador] = useState<string>("");
  const navigate = useNavigate();


  const updateInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setBuscador(e.target.value);
  };



  const changePage = (e: React.MouseEvent<HTMLButtonElement>) => {
    navigate(buttonLink, {replace: true});
  }
  return (
    <div>
      <h2 style={{ marginBottom: "20px", fontSize: "3rem" }}>
        <strong>{title}</strong>
      </h2>
      <section
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
        }}
      >
        <input
          type="search"
          placeholder={`Buscar ${title?.toLowerCase()}...`}
          style={{ paddingLeft: "10px" }}
          onChange={updateInput}
        />
        <button
            onClick={changePage}
          style={{
            backgroundColor: "#F5E51A",
            borderRadius: "5px",
            color: "#334FFF",
            fontSize: "1.5rem",
            border: "none",
            padding: "10px",
            right: "0",
          }}
        >
          <strong>Nuevo</strong>
        </button>
      </section>
      <section>
        <table>
          <thead>
            <tr>
              {
                columns?.map((column: string) => {
                    return <th>{column}</th>
                    })
              }
            </tr>
          </thead>
          <tbody>
            {data?.filter((acc : any) => 
                typeof acc[searchField] !== "undefined" && acc[searchField] !== null && acc[searchField].toLowerCase().includes(buscador.toLowerCase()) 
              )
              .map((acc: any, index: any) => {
                console.log(acc); 
                return (
                  <tr key={index}>
                   { keys?.map((key: string) => {
                    console.log(key);
                        return typeof acc[key]!=='boolean' ?  <td>{acc[key]}</td> : <td>{acc[key] ? 'activo' : 'inactivo'}</td>
                    })}
                    <td>
                      <button value = {acc[id]} onClick={deleteAction}>eliminar</button>
                      <br />
                      <button value = {acc[id]} onClick={updateAction}>editar</button>

                    </td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </section>
    </div>
  )
}

export default Table