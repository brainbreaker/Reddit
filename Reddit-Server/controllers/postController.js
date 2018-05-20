var Post = require('../models/Post.js');

exports.listAllPosts = (req, res) => {
  Post.find({}).sort({votes: 'desc'}).limit(20).exec(function(err, post) {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.createPost = (req, res) => {
  let newPost = new Post(req.body);
  console.log("Creating a new post - " + newPost);
  newPost.save((err, post) => {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.readPost = (req, res) => {
  Post.findById(req.params.postID, (err, post) => {
    if (err) {
      res.status(500).send(err);
    }
    res.status(200).json(post);
  });
};

exports.updatePost = (req, res) => {
  Post.findOneAndUpdate(
    { _id: req.params.postID },
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


exports.upvotePost = (req, res) => {
  Post.findById(req.params.postID, (err, post) => {
      if (err) {
        res.status(500).send(err);
      }

      // Increment the count of votes
      post.votes = post.votes + 1;
      post.save();

      res.status(200).json({message: "Post Upvoted successfully"});
    }
  );
};

exports.downvotePost = (req, res) => {
  Post.findById(req.params.postID, (err, post) => {
      if (err) {
        res.status(500).send(err);
      }
      
      // Let's allow to downvote only when votes are > 0
      if(post.votes > 0) {
        post.votes = post.votes - 1;
        post.save();
        res.status(200).send({message: "Post downvoted successfully"});
      }
    }
  );
};