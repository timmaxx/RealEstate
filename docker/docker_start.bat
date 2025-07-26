docker run -p 5442:5432 -d --name realestate01_db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=realestate postgres

rem -p: преобразование портов HOST PORT:CONTAINER PORT (или -P: все порты)
rem -d: флаг запускает контейнер в фоновом режиме (как демон)
rem --name [имя]: устанавливает имя демона для нового контейнера
rem -e: задание переменной окружения
rem postgres - наименование имиджа докера

rem Основные команды Docker:

rem посмотреть все контейнеры:
rem docker ps -a

rem остановить наш контейнер:
rem docker stop realestate01_db

rem запустить его:
rem docker start realestate01_db

rem удалить
rem docker rm realestate01_db

rem справка по командам:
rem docker help