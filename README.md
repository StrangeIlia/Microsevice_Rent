Микросервис, связанный с арендой книг. Выполнил студент ВТ-41 Бойчук Илья

Требуется:
1)	От сервиса пользователей: работа с его счетом и получение id по реквизитам.
2)	От сервиса книг: стоимость покупки и аренды данного типа книги, есть ли такая книга такого типа на складе (магазина) и 
    резервирование экземпляра книги по id транзакции.

Описание работы:

Заказ (книга выбрана и пользователь подтвердил, что он ее выбирает): 
1)	Клиент приходит с реквизитами и с id типа книги и сроком желаемой аренды.
2)	Запрос к сервису книг. Отправляю id типа книги. Желаю получить результат операции, стоимость покупки и аренды книги и id транзакции. 
    Если операция успешна, то 3, иначе 6. 
3)	Запрос к сервису пользователей. Отправляю стоимость покупки и реквизиты. 
    Желаю получить результат операции и id пользователя. Если успешно, то 4, иначе 6.
4)	Запрос к сервису книг. Отправляю id транзакции, чтобы ее закрепить.
5)	Отправляет пользователю ответ, что операция успешна завершена. Конец.
6)	Отправляем пользователю ответ, что операция не может быть завершена. 

Возращения залога.
1)	Клиент приходит с реквизитами и с списком из id типов книг (могут повторяться), которые он вернул.
2)	Запрос к сервису пользователей. Отправляю реквизиты. Желаю получить результат операции и id пользователя. 
    Если результат отрицательный, то 7, иначе 3.
3)	Если арендованных книг нет, то 6, иначе 4.
4)	Запрос к сервису пользователей. Отправляю сумму для пополнения счета и реквизиты пользователя. 
    Желаю получить результат операции. Если операция успешна, то 5, иначе 7.
5)	Запрос к сервису книг. Отправляю список из id типов книг (могут повторяться).
6)	Отправляю пользователю ответ, что операция завершена успешно. Конец.
7)	Отправляю пользователю ответ, что операция не может быть завершена.

Запросы от клиента: 
1)	Клиент приходит с реквизитами.
2)	Запрос к сервису клиентов. Отправляю реквизиты. Желаю получить результат операции и id пользователя. 
    Если результат операции отрицателен, то 7, иначе 3.
3)  Если в пункте 1 явно указан id заказа, то 4, иначе 5
4)	Если id пользователя совпадает с ответом сервиса пользователя то 6, иначе 7.
5)	Отправляю ему весь список его заказов. Конец.
6)	Отправляю ему id операции, стоимость аренды, покупки, тип книги, время начала аренды и ее срок. Конец.
7)	Отправляю клиенту ответ, что реквизиты не валидны.
