---
layout: post
title: Изобретаем on duplicate key update для SQLite
---

##Изобретаем on duplicate key update для SQLite

Движок моего сайта molva.spb.ru использует в качестве БД sqlite. Выбор пал на нее потому, что она подходит для задачи и мне давно хотелось попробовать sqlite в деле.

На практике, конечно, оказалось, что не все так гладко, как я предполагал. Например, sqlite не умеет делать file-per-table, при случайных delete и insert база быстро фрагментируется и чтение замедляется...

А еще в sqlite нет on duplicate key update при insert.

В моем случае это особенно печально, т.к. требуется обновлять словари частотности словоформ и биграмм.

Сначала леммы и словоформы пишутся в лог следующей структуры:  
    CREATE TABLE IF NOT EXISTS tweets_words(
        id integer,
        noun_md5 integer,
        source_md5 integer,
        PRIMARY KEY(id, noun_md5, source_md5)
    )
Логи хранятся в отдельных подневных базах. В терминах SQLite это означает, что для каждого дня есть отдельный файл, в каждом из которых есть табличка tweets_words.

Затем заполняем словарь частотности:
    CREATE TABLE IF NOT EXISTS tweets_words_freq(
        noun_md5 integer,
        source_md5 integer,
        cnt integer,
        PRIMARY KEY(id, noun_md5, source_md5)
    )

    INSERT INTO tweets_words_freq
    SELECT noun_md5, source_md5, count() 
    FROM tweets_words
    GROUP BY noun_md5, source_md5

Это работает для одного куска лога. А что, если нужно делать по нескольким кускам?
В MySQL мы бы сделали так:

    % для каждого куска лога %
    INSERT INTO tweets_words_freq
    SELECT noun_md5, source_md5, count() as cnt_new
    FROM tweets_words
    ON DUPLICATE KEY UPDATE cnt = cnt + cnt_new
    GROUP BY noun_md5, source_md5

Для SQLite нужен обходной путь.
Какие варианты приходят в голову:
1) Посчитать все инкременты в коде, в базу вставить REPLACE для новых значений.
2) Воспользоваться временной таблицей без ключей.

Первый вариант - это не спортивно:) Хотя именно его рекомендуют на StackOverflow.
Про второй вариант я расскажу далее.

Для начала, структура временной таблицы:

    CREATE TABLE IF NOT EXISTS tweets_words_freq_tmp(
        noun_md5 integer,
        source_md5 integer,
        cnt integer
    )

Такую структуру можно следующим запросом:
     CREATE TABLE tweets_words_freq_tmp 
     AS SELECT * 
     FROM tweets_words_freq 
     LIMIT 0

Этот запрос создает копию таблицы без ключей (и без записей).

Дальше, для всех кусков лога выполняем наш insert:

    INSERT INTO tweets_words_freq_tmp
    SELECT noun_md5, source_md5, count() 
    FROM tweets_words
    GROUP BY noun_md5, source_md5

Когда закончили, делаем:

    DELETE FROM tweets_words_freq;

    INSERT INTO tweets_words_freq
    SELECT noun_md5, source_md5, sum(cnt) 
    FROM tweets_words_freq_tmp
    GROUP BY noun_md5, source_md5;

Для пущей надежности, эти два запроса стоит обернуть в транзакцию.

У данного метода есть недостаток - все данные перед суммированием дублируются. Если нужно просуммировать N кусков лога, сначала придется их записать в один большой лог. Размер этого большого лога будет равен O(N * размер конечного словаря частотности).

Плюс метода - его простота. Вместо одного запроса `INSERT ... ON DUPLICATE KEY UPDATE мы сделали 4: CREATE TABLE, INSERT (N раз), DELETE, INSERT`. А про преобразование данных в объекты и обратно не пришлось думать вообще:)
