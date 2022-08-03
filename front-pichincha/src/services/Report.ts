import { Pageable, } from "../types/types";
import { DateRange } from "../types/types";
const API_URL = process.env.API_URL || 'http://localhost:8080';

export const getReport = async (clientId : string, dateRange: DateRange, pageable: Pageable, type?: string|undefined)  => {
    let url ;
    if(typeof type === 'undefined'){
        url = `${API_URL}/api/cuentas/reporte/${clientId}?startDate=${dateRange.startDate}&endDate=${dateRange.endDate}&size=${pageable.size}&page=${pageable.page}`;
    }
    else{
        url = `${API_URL}/api/cuentas/reporte/${clientId}?startDate=${dateRange.startDate}&endDate=${dateRange.endDate}&size=${pageable.size}&page=${pageable.page}&format=${type}`;
    }
    const response = await fetch(url);
    const data = await response.json();
    return data;

}