# ITMO-Programming

Содержательные части домашек с курса "Введения в программирование" 1 курса ИТМО.

## IntList

Часто нам хочется массив не статического размера
и очень часто мы работаем с числами,
`ArrayList<Integer>` прекрасный вариант, 
если закрыть глаза на большой перерасход памяти на хранение числа в обертке.

Для этого был написано компромисное решение,
семантически очень близко повторяющее `ArrayList<Integer>`,
но при этом хранящее внутри себя данные очень эффективно.

## FastScanner

Иногда возникает необходимость удобно считать большой массив данных.

Для этих целей у нас есть стандартный `Scanner` и `Reader`. Проблема первого в том,
что он достаточно медленный, а второго в том, что он читает только посимвольно.

Для этого было был написан `scanner`, как обертка над `Reader`,
и определены наиболее часто востребованные методы:
считать число, слово, строку и проверки их наличия.

Чтобы ускорить чтених, хотелось избавиться от бесполезного пробегания по массиву,
один их таких частых пробегов без результата -- проверка на наличие следующего.
В обход этого метода были введени дополнительные методы с суффиксами `InLine`.
Отличие такого метода от обычного -- это то, что он не выбратывает ошибку,
а возврашает `null`, такой результат следует интерпретировать, как отсутствие такого токена,
и в случае если мы считывали в какои-либо цикле, как сигнал к завершению.

## Markup

Задание на представление текста, как набора вложенных классов,
с последующим форматированием. В данном случае в Markdown и BBCode.

Из типов форматирования, кроме текста без форматирования, представленны:
полужирный, курсив и зачекнутый.
Вместе с Параграфом, он же контейнер строчек с форматированием и без,
представлены списки: нумерованый и нет.

## Game

Игра, которая играет сама в себя.

MNK-game -- что-то типа крестиков-ноликов, но на большем поле.

В данном проекте представлены:
* Доска и классы, для безопасного взаимодействия с ней,
чтобы игрик не имел возможности испортить доску и игру.
Досок несколько видов: квадратная и гексогональная.
* Игроки с различными стратегиями,
в том числе интерактивный `HumanPlayer`.
* Игра, на двоих и турнир на несколько игроков.

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

