rem GET
curl http://localhost:8080/real-estate/


rem 1. Admin users

rem 1.1. Admin. Get all
curl -X GET ^
  http://localhost:8080/real-estate/rest/admin/users

rem 1.2. Admin. Create one
curl -X POST ^
  http://localhost:8080/real-estate/rest/admin/users ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"New7Новый\", \"email\": \"new7@yandex.ru\", \"password\": \"passwordАбвгде\", \"roles\": [\"USER\"]}"


rem 2. One admin user

rem 2.1. Admin. Get one
curl -X GET ^
  http://localhost:8080/real-estate/rest/admin/users/100000

rem 2.2. Admin. Update one
curl -X PUT ^
  http://localhost:8080/real-estate/rest/admin/users/100000 ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"UserUpdatedОбновлённый\", \"email\": \"user@yandex.ru\", \"password\": \"passwordNew\", \"roles\": [\"USER\"]}"


rem 3. Not admin user

rem 3.1. Not admin. Get one
curl -X GET ^
  http://localhost:8080/real-estate/rest/profile

rem 3.2. Not admin. Update one
curl -X PUT ^
  http://localhost:8080/real-estate/rest/profile ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"New777Новый\", \"email\": \"new777@yandex.ru\", \"password\": \"passwordNewНовый\", \"roles\": [\"USER\"]}"

rem 3.3. Not admin. Delete one
curl -X DELETE ^
  http://localhost:8080/real-estate/rest/profile
