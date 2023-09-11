class BankAccount:
    def __init__(self, account_number, balance=0):
        self.account_number = account_number
        self.balance = balance

    def deposit(self, amount):
        self.balance += amount

    def withdraw(self, amount):
        if amount <= self.balance:
            self.balance -= amount
        else:
            print("Insufficient funds")

    def check_balance(self):
        print(f"Account {self.account_number} balance: ${self.balance}")

account1 = BankAccount("12345")
account2 = BankAccount("67890", 1000)

account1.deposit(500)
account1.withdraw(200)

account2.deposit(200)
account2.withdraw(1500)


account1.check_balance()
account2.check_balance()
