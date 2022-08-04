import { Movement } from "../types/types";
const API_URL = process.env.API_URL || 'http://localhost:8080';

export const getMovements = async ()  => {
    const response = await fetch(`${API_URL}/api/movimientos/listarTodos`);
    const data = await response.json();
    return data ;
}

export const postMovement = async (client : Movement) => {
    const response = await fetch(`${API_URL}/api/movimientos/guardar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)    })
    const data = await response.json();
    return data ;
}

export const putMovement = async (client : Movement) => {
    const response = await fetch(`${API_URL}/api/movimientos/actualizar`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)    })
    const data = await response.json();
    return data ;
}

export const deleteMovement = async (id : number) => {
    const response = await fetch(`${API_URL}/api/movimientos/eliminar/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const data = await response.json();
    return data ;
}

export const searchMovement = async (id : string) => {
    const response = await fetch(`${API_URL}/api/movimientos/buscar/${id}`);
    const data = await response.json();
    return data ;
}