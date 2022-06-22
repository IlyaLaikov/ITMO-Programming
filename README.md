# ITMO-Programming

## Expressions

### Base

Базовая часть представляет из себя набор классов:
константа, переменная, унарные и бинарные операторы.

Для всех классов переопределен метод `toString`,
все классы реализуют функциональные интервейсы
`Expression`,`TripleExpression` и `ToMiniString`.
Первые два вычисляет выражение с одной или тремя переменными,
третий генерирует строку с минимальным числом скобок.

### Exceptions

Добавляются классы, гарантирующие отсутствие переполнения
или кидающие исключение, и соответствующие исключения.

### Parser

Два эффективных парсера, защищенный и нет,
разбирающие выражение за один проход слева направо.
