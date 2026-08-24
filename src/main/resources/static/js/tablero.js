// TODO-GA-12: Consumo de servicios REST desde JavaScript (cliente)

class ErrorApi extends Error {
  constructor(titulo, detalle, estado) {
    super(`${titulo} (${estado})`);
    this.titulo = titulo;
    this.detalle = detalle;
    this.estado = estado;
  }
}

// TODO-GA-12: Cargar lotes desde la API y pintarlos en el tablero
async function cargarLotes(estado = null) {
  const cuerpo = document.querySelector('#tabla-lotes tbody');
  const aviso = document.querySelector('#aviso');
  mostrarCargando(cuerpo);

  const url = new URL('/api/v1/lotes', window.location.origin);
  if (estado) {
    url.searchParams.set('estado', estado);
  }

  try {
    const respuesta = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      }
    });

    // fetch NO lanza excepcion ante 4xx o 5xx: hay que comprobarlo
    if (!respuesta.ok) {
      const problema = await respuesta.json();
      throw new ErrorApi(problema.title, problema.detail, respuesta.status);
    }

    const lotes = await respuesta.json();
    pintarLotes(cuerpo, lotes);
    aviso.textContent = `${lotes.length} lotes encontrados`;

  } catch (error) {
    if (error instanceof ErrorApi) {
      aviso.textContent = `${error.titulo}: ${error.detalle}`;
    } else {
      aviso.textContent = 'No se pudo contactar al servidor. Reintente.';
      console.error('Fallo de red al cargar lotes', error);
    }
    cuerpo.innerHTML = '';
  }
}

// TODO-GA-12: Cargar pronostico de secado
async function cargarClima() {
  const contenedor = document.querySelector('#clima-secado');
  if (!contenedor) return;

  try {
    const respuesta = await fetch('/api/v1/clima/secado', {
      headers: { 'Accept': 'application/json' }
    });

    if (!respuesta.ok) {
      throw new Error(`HTTP ${respuesta.status}`);
    }

    const datos = await respuesta.json();
    if (datos.disponible) {
      contenedor.innerHTML = `
        <p><strong>Pronostico 48h:</strong></p>
        <p>Temperatura: ${datos.temperaturas?.[0] ?? 'N/A'} °C</p>
        <p>Humedad: ${datos.humedades?.[0] ?? 'N/A'} %</p>
      `;
    } else {
      contenedor.innerHTML = '<p class="text-warning">Clima no disponible</p>';
    }
  } catch (error) {
    contenedor.innerHTML = '<p class="text-muted">Error al cargar clima</p>';
    console.error('Error al cargar clima:', error);
  }
}

function pintarLotes(cuerpo, lotes) {
  if (!lotes || lotes.length === 0) {
    cuerpo.innerHTML = '<tr><td colspan="6" class="text-center">No hay lotes</td></tr>';
    return;
  }

  cuerpo.innerHTML = lotes.map(lote => `
    <tr>
      <td>${lote.codigo}</td>
      <td>${lote.fincaNombre ?? 'Sin finca'}</td>
      <td>${lote.fechaRecepcion}</td>
      <td>${lote.pesoKg} kg</td>
      <td>${lote.humedadPorcentaje ?? 'N/A'} %</td>
      <td>
        <span class="badge ${lote.estado === 'ACEPTADO' ? 'bg-success' :
          lote.estado === 'RECHAZADO' ? 'bg-danger' : 'bg-warning'}">
          ${lote.estado}
        </span>
      </td>
    </tr>
  `).join('');
}

function mostrarCargando(cuerpo) {
  cuerpo.innerHTML = '<tr><td colspan="6" class="text-center">Cargando...</td></tr>';
}

function obtenerToken() {
  return localStorage.getItem('token') ?? '';
}

// Cargar datos al inicio
document.addEventListener('DOMContentLoaded', () => {
  cargarLotes();
  cargarClima();
});
