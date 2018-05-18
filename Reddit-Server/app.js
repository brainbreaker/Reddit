const express = require('express');
const app = express();
require('./controllers/postController.js')(app);
// Database instance connection
require('./config/db.js');

const port = process.env.PORT || 3000;

app.get('/', (req, res) => res.send('Hello!'));

app.listen(port, () => console.log('Reddit is listening on port ' + port));
