#### Приложение - серверная часть микросервиса для обмена сообщениями

#### Рекомендации для сборки и запуска проекта:
```
-Перед стартом приложения заполнить переменные среды окружения
DATABASE_USERNAME=your_DB_username
DATABASE_PASSWORD=yuor_DB_password
MICROM=name_of_DB
Либо в properties.yml заменить значения указанных переменных
```
- Перейти в корень проекта
- Создать исполняемый **jar** командой  **mvn clean package** (или **mvn clean package --D maven.test.skip **)
- Запустить **jar** файл из командной строки: **java -jar target/micromessage-1.0.jar**
- Либо **mvn spring-boot:run** (сборка и старт)
- Приложение стартует на порту 8080.


  **Для тестирования приложения локально добавлен Swagger:** (доступен на localhost:8080/swagger-ui/index.html#)

  **Или через Postman**
- отправка сообщения: GET запрос http://localhost:8080/api/message - в теле запроса ввести текст сообщения
- получение сообщения по айди GET запрос http://localhost:8080/api/message/1
- получение списка сообщений в пределах дат POST запрос
  http://localhost:8080/api/message/from/2022-03-23T08:23:32/to/2022-03-25T21:23:32
  даты в формате: yyyy-mm-ddThh:mm:ss
