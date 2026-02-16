# Dispatch Windows – Full Stack Application
Sistema de selección y reserva de ventanas de despacho, con manejo de concurrencia mediante Redis, backend en Spring Boot y frontend en React + Vite.

# 🚀 Tecnologías
## Backend
* Java 17
* Spring Boot
* Spring Data JPA
* Redis (locks temporales)
* PostgreSQL

## Frontend
* React 18
* Vite
* TypeScript
* Nginx

## Infraestructura
* Docker

# 🐳 Levantar el proyecto con Docker
## Requisitos
* Docker
* Docker Compose

## Levantar todos los servicios
bash
`docker-compose up --build`
Esto ejecutara un insert en postgresql con 3 postal codes para pruebas `"7500000", "7500001", "7500002"`

## Esto levantará:

| Servicio  | URL |
| ------------- | ------------- |
| Frontend  | http://localhost:3000  |
| Backend  | http://localhost:8080  |
| Redis  | puerto interno  |
| PostgreSQL  | puerto interno  |

# 🔁 Flujo funcional
* Ingresar código postal
* Consultar fechas disponibles
* Consultar slots por fecha
* Seleccionar slot (lock en Redis)
* Confirmar reserva
* Refresco automático de disponibilidad
* Slot reservado queda deshabilitado

# 🔐 Concurrencia
* Cada selección de slot crea un lock temporal en Redis
* El lock está asociado a un orderId único
* Si otro cliente intenta reservar el mismo slot: Se responde 409 CONFLICT
* Al confirmar la reserva: Se descuenta capacidad
* Se libera el lock

# 🧪 Postman Collection
Se incluye una colección de Postman con todos los endpoints:
📂 Dispatch windows.postman_collection.json

## Endpoints incluidos:
* GET date availability by postal code
* GET date availability by postal code (NOT FOUND)
* GET time slot by date
* POST select time slot by id (Lock OK)
* POST select time slot by id (CONFLICT)
* POST Confirm Reservation

Importar la colección en Postman y ejecutar los requests en orden.

# 🧠 Decisiones de diseño
* El backend es la única fuente de verdad
* Ideal seria integrar una api para buscar la dirección 
* El frontend no muta estados manualmente
* La disponibilidad siempre se refresca desde el backend
* No se exponen entidades JPA directamente (uso de DTOs)
* Manejo explícito de errores (409, errores genéricos)
* UX básico (sin resets ni desaparición de componentes)
