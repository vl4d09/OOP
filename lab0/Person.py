class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def say_hello(self):
        print(f"Hello, my name is {self.name} and I am {self.age} years old.")


person1 = Person("Vlad", 19)
person2 = Person("Victor", 23)


person1.say_hello()
person2.say_hello()
