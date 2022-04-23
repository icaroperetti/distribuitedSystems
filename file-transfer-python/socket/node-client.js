const ip = "192.168.127.1";
const port = 8000;

let extensionFile;

const { on } = require("events");
var fs = require("fs");
var net = require("net");
var readLine = require("readline");

const client = new net.Socket();

var fileName = readLine.createInterface({
  input: process.stdin,
  output: process.stdout,
});

client.connect(8000, "192.168.127.1", () => {
  console.log("Digite o nome do arquivo: ");
  fileName.addListener("line", (line) => {
    file_ext = line.split(".")[1];
    file_name = line.split(".")[0];
    client.write(line);
    console.log("Receiving!");
  });
});

client.on("data", (data) => {
  fileName = `receivedfile_${file_name}.${file_ext}`;
  fs.appendFileSync(fileName, data, () => {
    console.log("Receiving data!");
  });

  client.on("close", () => {
    console.log("Connection closed");
  });
});
