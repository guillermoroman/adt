# Ejercicio - Ficheros de Intercambio de Datos
Toma como referencia el siguiente archivo `XML` e intenta representar su contenido en otros de los formatos de intercambio de datos más reconocidos:
- JSON
- CSV
- YAML

### XML original

```
<?xml version="1.0" encoding="UTF-8"?>
<clientes>
	<cliente DNI="78901234X">
		<apellidos>NADALES</apellidos>
		<CP>44126</CP>
	</cliente>
	<cliente DNI="89012345">
		<apellidos>ROJAS</apellidos>
		<validez estado="borrado" timestamp="1528286082" />
	</cliente>
	<cliente DNI="56789012B">
		<apellidos>SAMPER</apellidos>
		<CP>29730</CP>
	</cliente>
</clientes>
```

### 2. **Traducción a JSON**

```json
{
  "clientes": [
    {
      "DNI": "78901234X",
      "apellidos": "NADALES",
      "CP": 44126
    },
    {
      "DNI": "89012345",
      "apellidos": "ROJAS",
      "validez": {
        "estado": "borrado",
        "timestamp": 1528286082
      }
    },
    {
      "DNI": "56789012B",
      "apellidos": "SAMPER",
      "CP": 29730
    }
  ]
}
```

### 3. **Traducción a YAML**

```yaml
clientes:
  - DNI: "78901234X"
    apellidos: NADALES
    CP: 44126
  - DNI: "89012345"
    apellidos: ROJAS
    validez:
      estado: borrado
      timestamp: 1528286082
  - DNI: "56789012B"
    apellidos: SAMPER
    CP: 29730
```

### 4. **Traducción a TOML**

TOML no maneja muy bien las listas complejas o arrays de objetos, pero se puede estructurar de la siguiente manera:

```toml
[[clientes]]
DNI = "78901234X"
apellidos = "NADALES"
CP = 44126

[[clientes]]
DNI = "89012345"
apellidos = "ROJAS"
  [clientes.validez]
  estado = "borrado"
  timestamp = 1528286082

[[clientes]]
DNI = "56789012B"
apellidos = "SAMPER"
CP = 29730
```

### 5. **Traducción a CSV**

El formato **CSV** es más limitado para representar estructuras anidadas como la validez del cliente, por lo que esta estructura se "aplanaría". Se puede representar así:

```csv
DNI,apellidos,CP,validez_estado,validez_timestamp
78901234X,NADALES,44126,,
89012345,ROJAS,,borrado,1528286082
56789012B,SAMPER,29730,,
```

### 6. **Traducción a Protocol Buffers (Protobuf)**

Para **Protobuf**, primero necesitas definir el esquema en un archivo `.proto`. Aquí tienes un ejemplo del esquema para este caso:

```proto
syntax = "proto3";

message Cliente {
  string DNI = 1;
  string apellidos = 2;
  int32 CP = 3;
  Validez validez = 4;
}

message Validez {
  string estado = 1;
  int64 timestamp = 2;
}

message Clientes {
  repeated Cliente clientes = 1;
}
```

