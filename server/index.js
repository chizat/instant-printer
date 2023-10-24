var Queue = require('queue-fifo');
var queue = new Queue();

const fs = require('fs');

const util = require('util');
const exec = util.promisify(require('child_process').exec);

const express = require('express')
const app = express()
const port = 3000

var sent = [];

fs.watch("../watched", (eventtype, filename) => {
    if(!sent.includes(filename))
    {
        var house = queue.dequeue() || "hogwarts";
        var command = `java -cp ../bin com.printer.PrinterAndText ../watched/${filename} ${house}`;
        console.log(command);
        exec(command).catch(err => console.log(err));
        sent.push(filename);
    }
});

app.get('/', (req, res) => {
    let playName = req.query.name;
    let value = playName.split("/")[1].split(".")[0];
    queue.enqueue(value);
    console.log(`${value} added.`)
    res.sendStatus(200)
})

app.listen(port, () => console.log(`Example app listening on port ${port}!`))