
# Acceso a APIs con Unity

## 1. Objetivos
- **Objetivo general**: Dominar la comunicación entre Unity y un servidor para obtener y enviar datos. Se utilizará como ejemplo una entidad Quest que representa misiones.
- **Objetivos específicos**:
  1. Aprender a serializar y deserializar objetos JSON con `JsonUtility`.
  2. Conocer el uso de `UnityWebRequest` y coroutines para el envío y la recepción de datos.
  3. Mostrar la información obtenida en la UI de Unity.
  4. Crear formularios simples para enviar datos al servidor (creación de nuevas misiones).
  5. Manejar la navegación entre escenas para dividir la funcionalidad (por ejemplo, “Listado de Quests” y “Crear Quest”).

---

## 2. Preparación del Entorno

### 2.1 Estructura de Escenas
Se sugiere tener al menos dos escenas, aunque pueden ser más para un proyecto más grande:
1. **QuestListScene**: Muestra la lista de misiones descargadas de la API.
2. **CreateQuestScene**: Permite al usuario introducir datos para crear (enviar) una nueva misión.

### 2.2 API
- Se asume la existencia de un servidor con un endpoint `http://localhost:8080/api/quests` (o el que corresponda) que acepte métodos **GET** y **POST**.  


---

## 3. Modelo de Datos: Definición de `Quest`

En Unity (C#) necesitamos clases serializables para mapear la información que llega desde el servidor y la que enviamos. Por ello, definimos la clase `Quest` y, opcionalmente, una clase contenedora `QuestResponse` si el servidor nos devuelve listas.

```csharp
using System;
using System.Collections.Generic;

[Serializable]
public class Quest
{
    public string questId;
    public string title;
    public string description;
    public bool isCompleted;
    public int reward;
}

[Serializable]
public class QuestResponse
{
    public List<Quest> quests;
}
```

- **Quest**: representa una misión con un identificador (`questId`), un título, una descripción, si está completada (`isCompleted`) y la recompensa (`reward`).
- **QuestResponse**: se puede usar para deserializar un JSON que contenga una lista de quests. Por ejemplo, si el servidor responde algo como:
  ```json
  [
    {
      "questId": "q1",
      "title": "Recoger 10 bayas",
      "description": "Busca bayas en el bosque",
      "isCompleted": false,
      "reward": 50
    },
    ...
  ]
  ```
  Podemos adaptar la estructura según cómo responda la API. Si la API ya devuelve un array puro, tal vez no necesitemos `QuestResponse`. En caso de un objeto con la propiedad “quests”, lo usaremos como contenedor.

---

## 4. Obtener Datos (GET) y Mostrar en la UI

Para “llamar” al servidor y obtener la lista de quests, usaremos `UnityWebRequest.Get()`. Crearemos un script, por ejemplo `QuestListManager`, que se asociará a un GameObject en la escena `QuestListScene`. Este script mostrará las misiones descargadas en un componente UI (por ejemplo, `TextMeshPro`).

### 4.1 Script de Ejemplo: `QuestListManager`

```csharp
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using TMPro;

public class QuestListManager : MonoBehaviour
{
    [Header("UI References")]
    public TMP_Text questListText;    
    // Aquí mostraremos la lista de quests en la interfaz

    private string getQuestsUrl = "http://localhost:8080/api/quests";

    void Start()
    {
        // Al iniciar la escena, hacemos la petición para obtener las quests
        StartCoroutine(FetchQuests());
    }

    IEnumerator FetchQuests()
    {
        // Preparamos la petición GET
        UnityWebRequest request = UnityWebRequest.Get(getQuestsUrl);
        yield return request.SendWebRequest();

        // Comprobamos el resultado
        if (request.result == UnityWebRequest.Result.Success)
        {
            string json = request.downloadHandler.text;

            // Adaptar la forma en que se parsea la respuesta al formato que devuelva la API
            // En este ejemplo, asumimos que es un array o una lista en JSON
            // y construimos un objeto QuestResponse para usar la lista "quests".
            // Si tu servidor responde directamente con un array, podemos deserializarlo de otra forma.
            QuestResponse response = JsonUtility.FromJson<QuestResponse>("{\"quests\":" + json + "}");

            // Actualizamos la UI
            questListText.text = "Lista de Quests:\n";

            foreach (Quest quest in response.quests)
            {
                questListText.text += 
                    $"- [{quest.questId}] {quest.title} (Recompensa: {quest.reward})\n";
            }
        }
        else
        {
            // Mensaje de error en la UI
            questListText.text = "Error al cargar quests.";
            Debug.LogError("Error al obtener quests: " + request.error);
        }
    }
}
```

### 4.2 Explicación del Código

- `getQuestsUrl`: Dirección del servidor donde se encuentran las quests.
- `StartCoroutine(FetchQuests());`: Llama a la corrutina que realiza la petición.  
- `UnityWebRequest.Get(...)`: Realiza una petición GET.  
- `yield return request.SendWebRequest();`: Espera de forma asíncrona hasta que se recibe la respuesta.  
- Si la petición tiene éxito (`request.result == UnityWebRequest.Result.Success`), se maneja el JSON recibido, convirtiéndolo a `QuestResponse`.
- Finalmente, se imprime una lista de quests en `questListText`.

> **Nota**: Dependiendo de cómo la API devuelva el JSON, puede que baste con:  
> ```csharp
> Quest[] questArray = JsonUtility.FromJson<Quest[]>(json);
> ```  
> o algún método auxiliar para arrays. Se debe ajustar al formato exacto de la respuesta.

---

## 5. Enviar Datos (POST) para Crear una Nueva Quest

Ahora, pasaremos a la escena `CreateQuestScene` para que la persona usuaria introduzca datos (ID, título, descripción, etc.) y se envíen al servidor.

### 5.1 Script de Ejemplo: `CreateQuestManager`

```csharp
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.Networking;

public class CreateQuestManager : MonoBehaviour
{
    [Header("UI References")]
    public TMP_InputField questIdInput;
    public TMP_InputField titleInput;
    public TMP_InputField descriptionInput;
    public Toggle isCompletedToggle;
    public TMP_InputField rewardInput;
    public TMP_Text feedbackText;

    [Header("Button")]
    public Button submitButton;

    private string postQuestUrl = "http://localhost:8080/api/quests";

    void Start()
    {
        // Asignamos la acción del botón "Crear quest"
        submitButton.onClick.AddListener(OnSubmitQuest);
    }

    void OnSubmitQuest()
    {
        // Comprobamos que los campos requeridos tengan contenido
        if (string.IsNullOrEmpty(questIdInput.text) ||
            string.IsNullOrEmpty(titleInput.text) ||
            string.IsNullOrEmpty(descriptionInput.text) ||
            string.IsNullOrEmpty(rewardInput.text))
        {
            feedbackText.text = "Por favor, completa todos los campos.";
            return;
        }

        // Creamos el objeto Quest con los datos introducidos
        Quest newQuest = new Quest
        {
            questId = questIdInput.text,
            title = titleInput.text,
            description = descriptionInput.text,
            isCompleted = isCompletedToggle.isOn,
            reward = int.Parse(rewardInput.text)
        };

        // Llamamos a la corrutina de envío (POST)
        StartCoroutine(PostQuest(newQuest));
    }

    IEnumerator PostQuest(Quest quest)
    {
        // Convertimos el objeto a JSON
        string jsonBody = JsonUtility.ToJson(quest);

        // Configuramos la petición POST
        UnityWebRequest request = new UnityWebRequest(postQuestUrl, "POST");
        byte[] bodyRaw = System.Text.Encoding.UTF8.GetBytes(jsonBody);
        request.uploadHandler = new UploadHandlerRaw(bodyRaw);
        request.downloadHandler = new DownloadHandlerBuffer();
        request.SetRequestHeader("Content-Type", "application/json");

        // Enviamos la petición y esperamos la respuesta
        yield return request.SendWebRequest();

        // Manejo de la respuesta
        if (request.result == UnityWebRequest.Result.Success)
        {
            feedbackText.text = "¡Quest creada con éxito!";
            // Aquí se podría navegar a la escena que muestra las quests,
            // por ejemplo, invocar un método que llame a otra escena:
            // Invoke("GoToQuestListScene", 1.5f);
        }
        else
        {
            feedbackText.text = "Error al crear la quest.";
            Debug.LogError("Error al enviar quest: " + request.error);
        }
    }

    // Método opcional para cambiar de escena
    // void GoToQuestListScene()
    // {
    //     SceneManager.LoadScene("QuestListScene");
    // }
}
```

### 5.2 Explicación del Código

- **Validación de campos**: Antes de enviar la nueva misión, se verifica que los campos no estén vacíos.
- **Instanciación de `Quest`**: Se crea el objeto con los datos del formulario.
- **Serialización a JSON**: `JsonUtility.ToJson(quest)`.
- **Petición POST**: Se configura la cabecera `Content-Type` a `"application/json"`.
- **En caso de éxito**: Se muestra un mensaje en la interfaz, y opcionalmente se navega de nuevo a `QuestListScene` para ver la nueva quest creada.

---

## 6. Navegación entre Escenas (Opcional)

Si el proyecto incluye un menú principal o varias escenas, se puede manejar con un script simple como:

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneController : MonoBehaviour
{
    public void LoadScene(string sceneName)
    {
        SceneManager.LoadScene(sceneName);
    }
}
```

Luego, en los botones de la UI, se llama a `LoadScene("QuestListScene")`, `LoadScene("CreateQuestScene")`, etc., para navegar.

