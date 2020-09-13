import requests
from requests.exceptions import HTTPError


url = "https://covid19-us-api.herokuapp.com/twitter"

payload = {}
headers= {}

response = requests.get(url)

#print(response.text.encode('utf8'))
response.raise_for_status()

Jsonresponse = response.json()
a = Jsonresponse["message"]
i = 0
b = a["tweets"]
counter = 0
print(a["username"])
for i in b:
    text = i["full_text"]
    date = i["created_at"]
    print(text)
    n = date.split("T")
    print(n[0])


