import { Client } from "../types/types";
const API_URL = process.env.API_URL || 'http://localhost:8080';

export const getClientes = async ()  => {
    const response = await fetch(`${API_URL}/api/clientes/listarTodos`);
    const data = await response.json();
    return data ;
}

export const postClient = async (client : Client) => {
    const response = await fetch(`${API_URL}/api/clientes/guardar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)    })
    const data = await response.json();
    return data ;
}