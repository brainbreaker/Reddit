var Post = require('../models/post');

module.exports = (app) => {

  /* Create */
  app.post('/posts', (req, res) => {
    // Instantiate the Post model instance
    var post = new Post(req.body);

    // save the post instance to database(MongoDB)
    post.save((err, post) => {
      // redirect to the root
      return res.redirect(`/`);
    })
  });

};