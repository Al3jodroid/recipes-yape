# YapeRecipe App
Una aplicación que consume un api externo para la consulta de recetas de comida, como entregable para una prueba técnica de desarrollo.

# Uso del app
Para una correcta ejecución de los requerimientos se requiere una conexión a internet, y hacer las búsquedas s debe hacer en inglés dada la definición de datos en el backend.

Ejemplos de búsqueda de ingredientes pueden ser "chicken", "fish", o platos como "soup", "pie".

Incluso solo palabras como "sa", "la", "pi" pueden arrojar bastantes resultados.

### API Externa
La aplicacion consume la información de recetas:
- [The Meal DB](https://www.themealdb.com/api.php)

La aplicación utiliza geocode para resolver ubicaciones GPS con:
- [Google geocode](https://developers.google.com/maps/documentation/geocoding/overview)

### Bibliotecas externas
Las bibliotecas que contribuyen a la creación de este proyecto son
- [OkHTTP](https://square.github.io/okhttp/) para ejecución de request
- [hilt](https://dagger.dev/hilt/) para inyección de dependencias
- [retrofit](https://square.github.io/retrofit/) para el manejo de conexiones al servidor
- [coil](https://coil-kt.github.io/coil/) para la carga de imágenes de un servidor externo

### Recursos Externos
Los iconos fueron generados en:
- [Icon Kitchen](https://icon.kitchen/) para iconos de splash y launcher.

### Arquitectura principal y comportamiento
Con ayuda de un MVVM (model-view-viewmodel) la aplicación tiene una única activity que carga toda la definición de UI con creación declarativa "composable" donde se desprende la definición del app y acto seguido la primera pantalla que comprende un campo de búsqueda y la presentación de una lista o mensajes de respuesta vacía a la consulta hecha.

Una vez el usuario toca uno de los elementos de la lista se carga otra pantalla (No Activity, ni fragment) creada en compose que presenta al usuario dos tabs, uno para el detalle de la receta y otro para la visualización del mapa, tal carga de información adicional se da por el paso del id de la receta seleccionada previamente y una consulta a otros componentes del app.

La forma como fue concebida la interacción de la UI con el negocio fue con ayuda de dos viewmodel (el de búsqueda de receta y el de carga de datos adicionales) que con la ayuda de ejecución de corutinas no bloqueantes (por definición) ejecutan llamados a un caso de uso para la búsqueda de una lista de recetas por su ingrediente o nombre, o por el id de una previamente ya seleccionada.

El caso de uso que tiene un contrato definido y reglas de inyección de dependencias tiene acceso a un repositorio que es quien resuelve la  devolución de respuesta al mismo en la búsqueda y acceso de información de recetas, que en este caso es resuelto por el llamado a un API implementado con retrofit que se conecta al servidor remoto.

El API ejecuta el llamado, y recibe la información que es transformada por el repositorio para independizar la forma de respuesta de la información y la del negocio, una vez entonces el caso de uso tiene la información, adiciona un cálculo de ubicación GPS basado en la descripción del origen de la receta, e incluso gregar a una estructura caché el objeto y reducir la cantidad de llamados externos.

Una vez el caso de uso retorna al viewmodel por medio de la cortina previamente mencionada, el viewmodel evalúa el estado del objeto recibido: nulo, correcto, vacío... y dependiendo de tal respuesta emite un estado que será interpretado por los composables en UI para presentar al usuario los resultados.

Cabe resaltar que mientras se hace un request, procesamiento y transformación de la informacion del server a la UI también se emite un estado "loading" desde el viewmodel para mantener informado al usuario de la ejecución en curso.


### Componentes del MVVM
- DTO: Objetos que modelan la información pura tal y como llegan del JSON pero permiten modelar en objetos internos de la aplicación la información.
- Data model: Objetos que almacenan la información extraida del los DTOs para su manejo en el negocio y su presentación en UI, independiente de los cambios de estructura definidos en el servidor.
- UseCase: Contratos definidos para las clases de negocio que manipular y calculan la información recibida.
- Interactor: La clase que implementa y cumple los contratos definidos por el UseCase y contiene la lógica de negocio de la aplicación (en este caso tambien una estructura de cache)
- Repository: La fuente de información que se encarga de resolver de dónde traer los datos pedidos por el interactor y se conecta en esta caso al API externo.
- ViewModel: El intermediario que permite recibir los eventos de UI y llamar recursos y componentes adicionales del app para la consulta y transformación de datos que también serán emitidos a UI por medio de flows creados en esta misma clase.
- Flows: La estructura que permite emitir los estados a interfaz gráfica correspondiente a la información traída por la capa de negocio, además de su estado (vacío, erróneo, correcto)

## Características especiales del desarrollo (IMPORTANTE)
- La aplicación tiene su lenguaje de lectura definido en inglés para que sea más correspondiente al API que retorna toda la información en inglés, por lo cual en ingreso de datos se debe hacer en inglés también. (aunque la palabra en español “taco” si funciona)
- La forma que el API devuelve tanto una lista de recetas basada en una palabra, es exactamente igual a una consulta por id: una lista de objetos, en el primer caso muchos, en el segundo uno solo.
- Aunque toda la información de una receta es retornada en la consulta por palabras, esta se guarda en un caché para su posterior uso, con la adición del cálculo de la ubicación GPS, y usando su id como índice de acceso a la misma receta de forma interna.
- La definición del "lugar de origen" de la receta encontrada en el API solo responde a valores como "chinese", "french", "mexican", por lo cual es requerido el uso del geocoder de google para buscar un valor asociado a tal palabra, e incluso para mejorar su búsqueda se le concatena la palabra "country".
- Algunos casos como enviar a la biblioteca de geocoding la composición "thai country" no arrojan resultados a diferencia de "mexican country" que si retorna una coordenada del país méxico, cuando no hay respuesta se comunica tal falla de forma controlada en UI.
- Desafortunadamente los ingredientes y cantidades son retornados en el JSON de la forma "strIngredient1":"Bread","strIngredient2":"Egg", hasta el número 20, para un mapeo de tales objetos en una lista iterable, se optó por un uso de "java-reflection" para tratar de hacer un recorrido de valores más óptimos que variable por variable (serian 40 líneas de lectura en total)
- Tal implementación de reflexión de atributos, no fue posible probarla en los test dada su complejidad y tiempo restante que se tenía para entregar.
- Los test tuvieron que ser movidos a la sección de instrumentación dada la dependencia del caso de uso con el calculado del GPS con el "context" de la aplicación (claro está, inyectado para evitar dependencias)
- Existe la implementación de un patrón observe adicional para el monitoreo del estado de conexión a internet del dispositivo.


### Construcción del proyecto
Clona o descarga la aplicación, ábrela en android studio, carga todas las librerías definidas en el gradle y asegúrate de especificar en el local.properties un MAPS_API_KEY=AIzaxxxxxxxxxxx para ejecutar las funcionalidades del mapa, o agrega la key directamente en el manifest reemplazando el valor ${MAPS_API_KEY}

### Debug/Develop APK 
- [YapeRecipe (.apk)](https://drive.google.com/file/d/15RTKMFZjEZzGbPc--SY_MWjFx5B-BJZd/view?usp=sharing) para descarga e instalacion, recuerda darle permisos a tu dispositivo de instalacion de aplicacion terceras mas alla del playstore.
## Autor
  - **Alejandro Rodríguez S.** 
    [Al3jodroid](https://github.com/Al3jodroid)
