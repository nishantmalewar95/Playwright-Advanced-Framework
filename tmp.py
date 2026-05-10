import requests 
r=requests.get('https://parabank.parasoft.com/parabank/requestloan.htm', timeout=20) 
print(r.status_code) 
text=r.text 
print(text.find('id=\" "amount\')) ; echo print(text.find('name=\amount\')) ; echo print(text.find('id=\downPayment\')) ; echo print(text.find('id=\fromAccountId\')) ; echo print(text.find('Apply Now')) ; echo print(text.find('id=\loanStatus\')) ; echo idx=text.find('id=\amount\') ; echo print(text[max(0,idx-100):idx+300]) ; python tmp.py ; del tmp.py
