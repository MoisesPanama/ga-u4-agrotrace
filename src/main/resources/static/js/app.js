// TODO-GA-11: Consumo de servicios REST desde JavaScript (cliente)
document.addEventListener('DOMContentLoaded', function() {
    console.log('AgroTrace - Aplicacion cargada');

    const API_BASE = '/api/v1';

    // Cargar inventario
    fetch(`${API_BASE}/lotes/inventario`)
        .then(response => response.json())
        .then(data => {
            console.log('Inventario:', data);
        })
        .catch(error => console.error('Error al cargar inventario:', error));

    // Cargar lotes
    fetch(`${API_BASE}/lotes`)
        .then(response => response.json())
        .then(lotes => {
            console.log(`Total lotes: ${lotes.length}`);
        })
        .catch(error => console.error('Error al cargar lotes:', error));
});

// Funcion para buscar lotes por productor
function buscarPorProductor(cedula) {
    return fetch(`/api/v1/lotes?estado=REGISTRADO`)
        .then(response => response.json())
        .then(lotes => lotes.filter(l => l.productorCedula === cedula));
}

// TODO-GA-11: Consumo desde cliente con manejo de errores
async function obtenerClima(ciudad) {
    try {
        const response = await fetch(`/api/v1/clima/${ciudad}`);
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error al obtener clima:', error);
        return {
            ciudad: ciudad,
            temperatura: 28,
            humedad: 75,
            descripcion: 'Datos no disponibles',
            error: true
        };
    }
}

// TODO-GA-11: Consumo de SOAP desde JavaScript
async function certificarLoteSOAP(loteId) {
    const soapRequest = `
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                          xmlns:tns="http://uteq.com/agrotrace/soap">
            <soapenv:Header/>
            <soapenv:Body>
                <tns:certificarLoteRequest>
                    <tns:loteId>${loteId}</tns:loteId>
                </tns:certificarLoteRequest>
            </soapenv:Body>
        </soapenv:Envelope>`;

    try {
        const response = await fetch('/ws/certificacion', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/xml;charset=UTF-8',
                'SOAPAction': ''
            },
            body: soapRequest
        });

        const text = await response.text();
        const parser = new DOMParser();
        const xml = parser.parseFromString(text, 'text/xml');
        const exito = xml.querySelector('exitoso')?.textContent === 'true';
        const mensaje = xml.querySelector('mensaje')?.textContent;

        return { exito, mensaje };
    } catch (error) {
        console.error('Error en SOAP:', error);
        return { exito: false, mensaje: 'Error de conexion' };
    }
}
