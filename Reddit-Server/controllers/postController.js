var Post = require('../models/Post.js');

exports.listAllPosts = (req, res) => {
  Post.find({}, (err, post) => {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.createPost = (req, res) => {
  let newPost = new Post(req.body);
  console.log("New Post - " + newPost);
  newPost.save((err, post) => {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.readPost = (req, body) => {
  Post.findById(req.params.postid, (err, post) => {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.updatePost = (req, res) => {
  Post.findOneAndUpdate(
    { _id: req.params.postid },
    req.body,
    { new: true },
    (err, post) => {
      if (err) {
        res.status(500).send(err);
      }
      res.status(200).json(post);
    }
  );
};

exports.deletePost = (req, res) => {
  Post.remove({ _id: req.params.postID }, (err, post) => {
    if (err) {
		res.status(500).json({
            error: err
        })      
    }
    res.status(200).json({ message: "Post successfully deleted" });
  });
};