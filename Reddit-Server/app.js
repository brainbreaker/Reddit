const express = require('express');
const app = express();
const bodyParser = require("body-parser");
const postController = require('./controllers/postController.js');
const initializeDB = require('./init/initializeDB.js');
// Database instance connection
require('./config/db.js');

const port = process.env.PORT || 8000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

initializeDB.initializeDatabase();

// API routes
app.get('/', (req, res) => res.send('Hello There! - Welcome to my dummy Reddit API. See the documentation at http://github.com/brainbreaker/reddit to play with me.'));

app
  .route("/posts")
  .get(postController.listAllPosts)
  .post(postController.createPost);

app
  .route("/posts/:postID")
  .get(postController.readPost)
  .put(postController.updatePost)
  .delete(postController.deletePost);

app
  .route("/posts/:postID/upvote")
  .put(postController.upvotePost)

app
  .route("/posts/:postID/downvote")
  .put(postController.downvotePost)



// Start the server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});