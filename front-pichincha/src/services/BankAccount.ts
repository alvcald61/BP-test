import { BankAccount } from "../types/types";
const API_URL = process.env.API_URL || 'http://localhost:8080';

export const getBankAccounts = async ()  => {
    const response = await fetch(`${API_URL}/api/cuentas/listarTodos`);     
    const data = await response.json();
    return data ;
}
export const postBankAccount = async (bankAccount : BankAccount) => {
    const response = await fetch(`${API_URL}/api/cuentas/guardar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bankAccount)    })
    const data = await response.json();
    return data ;
}

export const getClientBakAccounts = async (clientId : number)  => {
    const response = await fetch(`${API_URL}/api/cuentas/cliente/${clientId}`);
    const data = await response.json();
    return data ;
}

export const putBankAccount = async (bankAccount : BankAccount) => {
    const response = await fetch(`${API_URL}/api/cuentas/actualizar`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bankAccount)    })
    const data = await response.json();
    return data ;
}

export const deleteBankAccount = async (id : number) => {
    const response = await fetch(`${API_URL}/api/cuentas/eliminar/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const data = await response.json();
    return data ;
}

export const searchBankAccount = async (id : string) => {
    const response = await fetch(`${API_URL}/api/cuentas/buscar/${id}`);
    const data = await response.json();
    return data ;
}