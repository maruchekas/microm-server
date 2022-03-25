#### Приложение - клиентская часть микросервиса для обмена сообщениями

#### Рекомендации для сборки и запуска проекта:
```
-Перед стартом приложения заполнить переменные среды окружения
DATABASE_USERNAME=your_DB_username
DATABASE_PASSWORD=yuor_DB_password
MICROM=name_of_DB
```
- Перейти в корень проекта
- Создать исполняемый **jar** командой  **mvn clean package** (или **mvn clean package --D maven.test.skip **)
- Запустить **jar** файл из командной строки: **java -jar target/micromessagemate-1.0.jar**
- Либо **mvn spring-boot:run** (сборка и старт)
- Приложение стартует на порту 8008.
  **Для тестирования приложения локально (например Postman):**
- отправка сообщения: GET запрос http://localhost:8008/api/message - в теле запроса ввести текст сообщения
- получение списка сообщений в пределах дат POST запрос
  http://localhost:8008/api/message/from/2022-03-23T08:23:32/to/2022-03-25T21:23:32
  даты в формате: yyyy-mm-ddThh:mm:ss