## Отчет по итогам автоматизированного тестирования
___


Было проведено автоматизированное тестирование комплексного сервиса заказа тура по карте, взаимодействующего с СУБД и API Банка. Для этого были реализованы автотесты на Java, представляющие собой позитивные и негативные проверки заполнения всех полей формы, а также проверки сохранения информации о платежах в собственной БД системы (с использованием MySQL и PostgreSQL). Для составленbя отчётности в код был интегрирован Allure Report.
* Всего тестов - 68
* Успешных - 28 (41,17%) / не успешных - 40 (58,82%)

![](C:\Users\Fosa\IdeaProjects\QADiplom\img\Reports.png)

По результатам проведённого тестирования сформированы баг-репорты (см. GitHub Issues).