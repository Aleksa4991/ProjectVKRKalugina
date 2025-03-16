Выпускная квалификационная работа Калугиной Александры.
Микросервис userservice, порт 9000 
Микросервис adservice, порт 9001
Две отдельных базы данных для микросервисов: PostgreSQL 16
advertisements http://127.0.0.1:5432/advertisements
userservice http://localhost:5432/userservice
Вход на БД: 
username: postgres 
password: 111
Frontend части проекта нет, проверка работы между микросервисами осуществлялась через Postman.
Сборка проекта с помощью Gradle
