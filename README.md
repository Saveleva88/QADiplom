## Дипломный проект профессии "Тестировщик"

___

### Документация по проекту

1. [План тестирования](https://github.com/Saveleva88/QADiplom/blob/master/Plan.md)
2. [Отчет по итогам автоматизированного тестирования](https://github.com/Saveleva88/QADiplom/blob/master/Report.md)
3. [Отчет по итогам автоматизации](https://github.com/Saveleva88/QADiplom/blob/master/Summary.md)

### Запуск приложения

Для запуска приложения необходимо следующее ПО:
* IntelliJ IDEA
* Docker

**Примечание**: Приложение запускалось через Docker на локальной машине.

* склонировать репозиторий на свой ПК командой ```git clone https://github.com/Saveleva88/QADiplom```
* В терминале IntelliJ IDEA с помощью команды ```docker-compose up -d``` разворачиваем контейнер, необходимый для дальнейшей работы (настройки для запуска контейнера прописаны в файле docker-compose.yml)
* в терминале IntelliJ IDEA запустить SUT:
    - с использованием БД MySQL
      командой ```java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar```
    - с использованием БД PostgreSQL
      командой ```java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar```
    
*В браузере сервис будет доступен по адресу http://localhost:8080/*

* запустить автотесты командой:
    - для конфигурации БД MySql:  
      ```./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" ```
    - для конфигурации БД PostgreSQL:  
      ```./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app" ```

* запустить отчет командой:
```./gradlew allureServe (запуск и открытие отчета)```

* остановить SUT комбдинацией клавиш ```CTRL+C```

* Остановить контейнеры командой ```docker-compose stop``` и после удалить контейнеры командой
  ```docker-compose down```