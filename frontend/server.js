const express = require('express');
const path = require('path');
const serveStatic = require('serve-static');

let app = express();
app.use(serveStatic(__dirname + "/dist"));
app.route('/*')
    .get(function(req, res) {
          res.sendFile(path.join(__dirname + '/dist/index.html'));
});

module.exports = app;
const port = process.env.PORT || 5000;
app.listen(port, () => {
  console.log('Listening on port ' + port)
});