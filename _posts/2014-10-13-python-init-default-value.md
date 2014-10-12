---
layout: post
title: Почему бы не сделать дефолтный dict? 
---

У меня был примерно такой python класс:

    class Mashup:

        def __init__(self, foo):
            self.bar={}
            self.foo=foo

Поле bar инициализируется пустым dict, затем заполняется методами класса.
Для тестирования потребовалось создать объект с уже готовым dict, и я придумал такой код: 

    class Mashup:

        def __init__(self, foo, bar={}):
            self.bar=bar
            self.foo=foo

Казалось бы, все в порядке. И например, в джаве это и правда было бы так. (Если бы у нее были значения конструкторы полей по умолчанию.)
Но на практике этот класс подставляет жирную свинью. Например, такой код не проходит assert.

    m1 = Mashup(1)
    m1.bar[1] = 2

    m2 = Mashup(2)

    print m2.bar

    assert id(m1.bar) != id(m2.bar)
    
    $ ./bug.py
    {1: 2}
    Traceback (most recent call last):
      File "./bug.py", line 16, in <module>
        assert id(m1.bar) != id(m2.bar)
    AssertionError

А случилось вот что:

### дефолтный аргумент создался ровно один раз. Видимо, переменные kwargs создаются с областью видимости static.

Примерно так:

    public class Mashup{

        private static Bar defaultBar = new Bar();
        Mashup(int foo) {
            this.foo = foo;
            this.bar = bar;
        }
    }

Тогда как я ожидал:

    public class Mashup{

        Mashup(int foo) {
            this.foo = new Bar();
            this.bar = bar;
        }
    }


Чувство, что меня жестоко обманули, не покидает :)
