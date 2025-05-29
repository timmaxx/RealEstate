package com.timmax.realestate.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.timmax.realestate.model.User;

import java.net.URI;
import java.util.List;

/* В приложении есть контроллеры для Admin и для User.
Чтобы сделать их REST-контроллерами, заменим аннотацию @Controller на @RestController.
Если зайти ч/з Ctrl+Click в @RestController: к аннотации @Controller добавлена @ResponseBody
(которая означает, что ответ сервера будет приходить в теле запроса).
Т.е. ответ от приложения будет не имя View, а данные в теле ответа.
В @RequestMapping
(будет применена ко всем методам этого контролера, и, вообще,
если вы не можете поставить эту аннотацию над классом, то у вас проблемы - класс не S (SOLID)),
кроме пути для методов контроллера (value),
добавляем параметр produces = MediaType.APPLICATION_JSON_VALUE.
Это означает, что в заголовки ответа для всех методов класса будет добавлен тип ContentType="application/json"
- в ответе от контроллера будет приходить JSON-объект (что и есть соответствие архитектурному стилю REST)
(для всех методов, возвращающих не void,
а для возвращающих void, ничего отдаваться не будет и нужно @ResponseStatus(HttpStatus.NO_CONTENT)).
Чтобы было удобно использовать путь к этому контроллеру в приложении и в тестах,
выделим путь к нему в константу REST_URL, к которой можно будет обращаться из других классов.
*/
@RestController
@RequestMapping(
        value = AdminRestController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE
)
/* При текущей реализации, если запустить и посмотреть в браузере
http://localhost:8080/real-estate/rest/admin/users
то получим ошибку:
- у меня "HTTP Status 500 – Internal Server Error"
- у преподавателя "HTTP Status 406 – Not Acceptable"
*/
public class AdminRestController extends AbstractUserController {
    public static final String REST_URL = "/rest/admin/users";

    /* Все комментарии и соответствующие им реализации - это СТАНДАРТ архитектурного стиля REST.
    НЕ придумывайте ничего своего в своих проектах!
    Это очень большая ошибка - не придерживаться стандартов API.
    */

    /* 1.
    Метод AdminRestController.getAll пометим аннотацией @GetMapping - маршрутизация к методу по HTTP GET.
    */
    @Override
    // Такой вариант @RequestMapping(method = RequestMethod.GET) использовался ранее и теперь есть сокращённая версия:
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    /* 2.
    Метод AdminRestController.get пометим аннотацией @GetMapping("/{id}").
    В скобках аннотации указано, что к основному URL контроллера будет добавляться id пользователя - переменная,
    которая передается в запросе непосредственно в URL.
    Соответствующий параметр метода нужно пометить аннотацией @PathVariable
    (если имя в URL и имя аргумента метода не совпадают,
    в параметрах аннотации дополнительно нужно будет уточнить имя в URL (т.е. @PathVariable("id")).
    Если они совпадают, этого не требуется, т.к. фреймворк может по байт-коду int id определить имя).
    */
    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    /* 3.
    Метод создания пользователя create отметим аннотацией @PostMapping - маршрутизация к методу по HTTP POST.
    В метод мы передаем объект User в теле запроса (аннотация @RequestBody)
    и в формате JSON (consumes = MediaType.APPLICATION_JSON_VALUE).
    При создании нового ресурса правила хорошего тона - вернуть в заголовке ответа URL созданного ресурса.
    Для этого возвращаем не User, а ResponseEntity<User> (из-за этого @Override не получится и имя метода меняем),
    который мы можем с помощью билдера ServletUriComponentsBuilder дополнить заголовком ответа Location
    и вернуть статус CREATED(201)
    (если пойти в код ResponseEntity.created можно докопаться до сути, очень рекомендую смотреть в исходники кода).
    */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    /* 4.
    Метод delete помечаем @DeleteMapping("/{id}") - HTTP DELETE.
    Он ничего не возвращает, поэтому помечаем его аннотацией @ResponseStatus(HttpStatus.NO_CONTENT).
    Статус ответа будет HTTP.204.
    И лучше так и делать, т.к. не все браузеры без такого ответа будут считать это нормальным
    (FireFox - считает ошибкой, Chrome - норм).
    */
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    /* 5.
    Над методом обновления ставим @PutMapping - HTTP PUT.
    В аргументах метод принимает @RequestBody User user (т.е. из тела запроса)
    и @PathVariable int id (т.е. из URL).
    */
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        super.update(user, id);
    }

    /* 6.
    Метод поиска по email также помечаем @GetMapping, и,
    чтобы не было конфликта маршрутизации с методом get(), указываем в URL добавку "/by".
    В этот метод email передается как параметр запроса - аннотация @RequestParam
    (согласно REST мы не можем передавать e-mail в URL).
    */
    @Override
    @GetMapping("/by-email")
    public User getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }
}
