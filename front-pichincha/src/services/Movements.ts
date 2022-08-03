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