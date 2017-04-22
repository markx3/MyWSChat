from suds.client import Client
import threading
import time
import sys
import os

reload(sys)
sys.setdefaultencoding('utf8')

url = "http://127.0.0.1:9876/chat?wsdl"
client = Client(url)

print "Insira seu nome de usuario:"
id = client.service.assignID(raw_input())
if id < 0: exit

def receiver(delay):
    messages = []
    while True:
        sys.stdout.flush()
        message = client.service.getMessage(id)
        if message is not None:
            print "\n" * 100
            messages.append(message)
            for i in range(len(messages)):
                print messages[i]
        time.sleep(delay)

def sender(delay):
    while True:
        client.service.sendMessage(raw_input(), id)
        time.sleep(delay)

t = threading.Thread(target=receiver, args=(0.05,))
t2 = threading.Thread(target=sender, args=(0.05,))

t.start()
t2.start()
