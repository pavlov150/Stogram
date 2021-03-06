# Stogram
Аналог Instagram (веб-версия)

Адрес в сети интернет: http://stogram.moscow/

**Старт проекта:** 15 марта 2022 г.

**Участие в проекте:**
В проекте может участвовать любой желающий. Нужны дизайнер и верстальщик

:computer: **Разворачивание и запуск проекта:**
1. Клонируем проект: git clone https://github.com/pavlov150/Stogram.git
2. Создаем БД в PostgreSQL и запускаем скрипт StogramDB.sql для создания всех необходимых таблиц и их первичного наполнения
3. Открываем проект в IntelliJ IDEA и в JpaConfig.java указываем имя и пароль от вашей БД и имя БД
4. Настраиваем Tomcat
5. Запускаем приложение

:white_check_mark: **Что реализовано:**
1. Регистрация пользователя
2. Авторизация и аутентификация пользователя
3. Создание поста
4. Редактирование поста
5. Удаление поста
6. Добавление/удаление комментариев к постам
7. Просмотр постов (3 варианта): плитка, список, просмотр одного поста
8. Редактирование профиля
9. Добавление аватарки в профиле
10. Подсчет количества постов у пользователя
11. Изменение размера изображения
12. Обрезка изображения
13. Форма обратной связи
14. Аватарки пользователей в комментариях
15. Просмотр страницы другого пользователя
16. Подписка на пользователей
17. Отписка от пользователей
18. Страница с постами по подписке
19. Тексты по умолчанию при отсутствии подписок и постов
20. Лайки
21. Добавление видео в формате mp4, mov
22. Поиск пользователей
23. Поиск постов по тегам
24. QR-код
25. REST-API

:abcd: **REST-API:**

Используется Basic-Auth

Выборка постов по ключевому слову

GET http://stogram.moscow/api/post?search=москва


Выборка всех постов

GET http://stogram.moscow/api/post


Поиск поста по ID

GET http://stogram.moscow/api/post/284


Создание поста

POST http://stogram.moscow/api/post/

Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API create",
    
    "createdAt": "2022-06-12 02:18:59"
}
```

Редактирование поста

PUT http://stogram.moscow/api/post/294

Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API edit",
    
    "createdAt": "2022-06-12 02:18:59"
}
```

Удаление поста

DELETE http://stogram.moscow/api/post/295


:iphone: **Внешний вид приложения на текущий момент:**

Страница пользователя без подписок

![2022-06-11_02-15-12](https://user-images.githubusercontent.com/15989675/173162035-7e5966cb-6fe5-462e-9eab-a91141f508fd.jpg)

Редактирование профиля

![2022-06-11_02-16-42](https://user-images.githubusercontent.com/15989675/173162059-9fcea065-275c-48cd-8554-d6d594e8c056.jpg)

Создание поста

![2022-06-11_02-16-59](https://user-images.githubusercontent.com/15989675/173162082-c23c1604-e3c1-4c10-836e-36ffdeb51106.jpg)

![2022-06-11_02-17-23](https://user-images.githubusercontent.com/15989675/173162100-e1d511b5-d6eb-46e4-a581-c821376ea52c.jpg)

![2022-06-11_02-17-52](https://user-images.githubusercontent.com/15989675/173162104-92728f23-4513-44bf-820a-aa80d1ba76ab.jpg)

![2022-06-11_02-18-09](https://user-images.githubusercontent.com/15989675/173162139-71dc521b-af7b-4e61-ad51-15b13dd21d26.jpg)

Страница с постами текущего пользователя

![2022-06-11_02-15-53](https://user-images.githubusercontent.com/15989675/173162151-cfc859ba-fe45-43a7-b04f-141567f019fb.jpg)

Изменение поста

![2022-06-11_02-18-29](https://user-images.githubusercontent.com/15989675/173162194-d93f375d-b7b9-41f9-aa32-1e3cb000c1ba.jpg)

Страница с постами по подписке

![2022-06-11_02-18-46](https://user-images.githubusercontent.com/15989675/173162214-bbee91b8-c6ab-4e2b-8b47-b0c7dd8cb7bf.jpg)

Страница пользователя на которого можно подписаться или отписаться

![2022-06-11_02-19-06](https://user-images.githubusercontent.com/15989675/173162299-cf8b966c-f69a-47ad-be12-d0912da7ee66.jpg)

Страница с подписчиками

![2022-06-11_02-19-34](https://user-images.githubusercontent.com/15989675/173162336-d7ca04f6-51a4-4ebe-9c9a-763c41d45961.jpg)

Страница с подписками

![2022-06-11_02-19-50](https://user-images.githubusercontent.com/15989675/173162357-a3a6e4fa-e4d3-4d0e-af6b-45bdd0242623.jpg)

Поиск пользователей и постов по тегам

![2022-06-11_02-20-35](https://user-images.githubusercontent.com/15989675/173162381-a2820b5b-08d2-46fc-b7c3-738bc37b82d5.jpg)

QR-код страницы пользователя

![2022-06-11_02-20-55](https://user-images.githubusercontent.com/15989675/173162398-5de577e3-aef2-413e-9548-57ea1e6b943f.jpg)

Страница авторизации

![2022-06-11_02-21-45](https://user-images.githubusercontent.com/15989675/173162409-312c8875-6031-4c17-9dac-276e0a4216a7.jpg)

Страница регистрации

![2022-06-11_02-22-23](https://user-images.githubusercontent.com/15989675/173162439-5eb72aa2-ebea-4641-a609-48ac8d217eb5.jpg)
