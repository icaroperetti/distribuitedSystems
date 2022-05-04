import Pyro4

name_server = Pyro4.locateNS()
uri = name_server.lookup("server")


obj = Pyro4.Proxy(uri)

print(obj.helloWorld())
