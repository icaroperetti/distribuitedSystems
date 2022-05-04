import Pyro4


@Pyro4.expose
class Server(object):
    def helloWorld(self):
        return "Hello World!"


daemon = Pyro4.Daemon()

uri = daemon.register(Server)

print(uri)

name_server = Pyro4.locateNS()
name_server.register("server", uri)

daemon.requestLoop()
