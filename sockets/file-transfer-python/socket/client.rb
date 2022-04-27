require 'socket'

#Requesting the server IP and PORT
puts "Enter the server IP:"
ip = gets.chomp

puts "Enter de server port"
port = gets.chomp

# Create a socket connection
socket = TCPSocket.open(ip, port)

# Requesting the file name to download
puts "Enter the file that you want:"
filename = gets.chomp 

# Sending the file name to the server
socket.send(filename,0) 

#Reading the message sent by the server
data = socket.read 

#Getting the message of file not found from the server
if data == "File not found"
  puts "File not found"
  exit
end

#Saving the data
destFile = File.open("./received-files/receivedfile_#{filename}", 'wb') 
destFile.print data 
destFile.close

#Closing the connection
socket.close