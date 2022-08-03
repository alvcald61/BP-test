import React, { useState, useEffect } from "react";
import { DateRange, Pageable } from "../../types/types";
import {  Client, Report } from "../../types/types";
import { getClientes } from "../../services/Clients";
import { getReport } from "../../services/Report";
import moment from "moment";

type Props = {};
type Request = {
  clientId: string; 
  dateRange: DateRange;
  pagebale: Pageable
}
type PageInfo = {
  page: number,
  size: number,
  totalElements: number
}

type Summary = {
  totalWithdrawal: number
  totalDeposit: number
}

const size = 5;

const Reportes = (props: Props) => {
  const [request, setRequest] = useState<Request>({} as Request);
  const [report, setReport] = useState<Report[]>([]);
  const [pageInfo, setPageInfo] = useState<PageInfo>({} as PageInfo);
  const [clients, setClients] = useState<Client[]>([]);
  const [summary, setSummary] = useState<Summary>({} as Summary);
  const [clientId, setClientId] = useState<number>();
  useEffect(() => {
    const getResult = async () => {
      const data = await getClientes();
      setClients(data.data);
    };
    getResult();
  }, []);

  const checkUndefined = (parameter:any) :boolean=> {
    return typeof parameter=== 'undefined'
  }


  const generateNumbers = () => {
    const numberOfPages = Math.ceil(pageInfo.totalElements/pageInfo.size);
    const arr:any[] = [];
    for(let i = 0; i < numberOfPages; i++){
      arr.push(
        <a href="#" onClick={onClickA}
        style={{
          margin: "10px"
        }}
        >{i+1}</a>
      )
    }
    return arr;
  }
  
  const onClickA = (e: React.MouseEvent<HTMLAnchorElement>)=>{
    e.preventDefault();
    const page = e.currentTarget.innerHTML
    getReportData(parseInt(page)-1);

  }
  
  const getReportData = async (page: number) =>{
    request.pagebale.size = size;
    request.pagebale.page = page;
    let data = await getReport(request.clientId, request.dateRange, request.pagebale)
    data = data.data
    setReport(data.content)
    setPageInfo({...pageInfo, "page": data.page, "size": data.size, "totalElements": data.totalElements})
    setSummary({totalWithdrawal: data.totalWithdrawals, totalDeposit: data.totalDeposits});
  }


  const changePage = (e: React.MouseEvent<HTMLButtonElement>) => {
    request.pagebale  = {size: size, page: 0 }
    if(checkUndefined(request.clientId) || checkUndefined( request.dateRange)|| checkUndefined(request.pagebale)){
      console.log(request)
      return;
    }
   
    getReportData(0);
  };

 

const onChangeDropdown = (e: React.ChangeEvent<HTMLSelectElement>) => {
  setClientId(parseInt(e.target.value));
  setRequest({...request, clientId: e.currentTarget.value});
}

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if(typeof e.target.name === 'undefined' || typeof e.target.value === 'undefined'){
      return;
    }
    let value = e.target.value;
    let data = moment(value).format('DD/MM/YYYY');
    setRequest({...request, "dateRange": {...request.dateRange,  [e.target.name]: data}});
  };

  const downloadPDF = (pdf:string) => {
    const linkSource = `data:application/pdf;base64,${pdf}`;
    const downloadLink = document.createElement("a");
    const client = clients.find(client => client.id === clientId);
    const fileName = `${client?.identifier}-${request.dateRange.startDate}-${request.dateRange.endDate}.pdf`;
    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  };


  const downloadReport = (e: React.MouseEvent<HTMLButtonElement>) => {
    const getData = async ()=>{
      let data = await getReport(request.clientId, request.dateRange, request.pagebale,"PDF")
      data = data.data
      console.log(data);
      downloadPDF(data);
    }
    getData();
    
  }

  return (
    <div>
      <h2 style={{ marginBottom: "20px", fontSize: "3rem" }}>
        <strong>Reporte de movimientos bancarios</strong>
      </h2>
      <section
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <div>
          <label style={{ margin: "10px" }}>Cliente: </label><br/>
          <select
            onChange={onChangeDropdown}
            name="clientId"
            id="clientId"
            style={{ margin: "10px", width: "230px", height: "30px" }}
          >
            <option value="" disabled selected hidden>
              Nombre - DNI
            </option>
            {clients.map((client: Client) => {
              return (
                <option
                  key={client.id}
                  value={client.id}
                >{`${client.name} - ${client.identifier}`}</option>
              );
            })}
          </select>
        </div>

        <div>
          <label style={{ margin: "10px", cursor: "default" }}>
            Fecha de Inicio:{" "}
          </label>
          <input
            onChange={onChange}
            type="date"
            id="startDate"
            name="startDate"
            style={{ margin: "10px", width: "50%", height: "30px" }}
          />
        </div>
        <div>
          <label style={{ margin: "10px", cursor: "default" }}>
            Fecha de fin:{" "}
          </label>
          <input
            onChange={onChange}
            type="date"
            id="endDate"
            name="endDate"
            style={{ margin: "10px", width: "50%", height: "30px" }}
          />
        </div>
        <div>

        <button
        onClick={changePage}
          style={{
            backgroundColor: "#F5E51A",
            borderRadius: "5px",
            color: "#334FFF",
            fontSize: "1rem",
            height: "40px",
            border: "none",
            padding: "10px",
            right: "0",
            marginRight: "20px",
          }}
        >
          <strong>
            Buscar
          </strong>
        </button>

        <button
          onClick={downloadReport}
          style={{
            backgroundColor: "#F5E51A",
            borderRadius: "5px",
            color: "#334FFF",
            fontSize: "1rem",
            height: "40px",
            border: "none",
            padding: "10px",
            right: "0",
          }}
        >
          <strong>Descargar reporte</strong>
        </button>
        </div>
      </section>
      <section>
        <table>
          <thead>
            <tr>
              <td>Cliente</td>
              <td>Numero de Cuenta</td>
              <td>Tipo</td>
              <td>Saldo Inicial</td>
              <td>Movimiento</td>
              <td>Saldo Disponible</td>
              <td>Fecha</td>
              <td>Estado</td>
            </tr>
          </thead>
          <tbody>
            {
              report.map((item: Report, index: number) => {
                return (
                  <tr key={index}>
                    <td>{item.client}</td>
                    <td>{item.accountNumber}</td>
                    <td>{item.accountType}</td>
                    <td>{item.initialBalance}</td>
                    <td>{item.amount}</td>
                    <td>{item.availableBalance}</td>
                    <td>{item.transactionDate}</td>
                    <td>{item.status?"Activo": "Inactivo"}</td>
                    </tr>
                )
            })
          }
          </tbody>
        </table>
      </section>
      <section>
      <label style={{ margin: "10px", cursor: "default" }}>
            Total de retiros: {summary.totalWithdrawal}
          </label>
          <br></br>
          <label style={{ margin: "10px", cursor: "default" }}>
            Total de depósitos: {summary.totalDeposit}
          </label>
      </section>
    <section
    style={{
      width: "100%",
      height: "50px",
      display: "flex",
      flexDirection: "row", 
      justifyContent: "center",
      alignItems: "center"
    }}
    >
         {
          generateNumbers()
         }
    </section>
    

    </div>
  );
};

export default Reportes;
