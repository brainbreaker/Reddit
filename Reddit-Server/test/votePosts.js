const chai = require('chai')
const chaiHttp = require('chai-http')
const should = chai.should()
const Post = require('../models/post');
var url = "http://localhost:" + (process.env.PORT || 8000);


describe('Upvote/Downvote a post', () => {
  it('Should be able to update votes of a post at /posts', (done) => {
      
      // Fetch all the posts, then try upvoting and downvoting the first in the index
      chai.request(url)
      .get('/posts')
      .end((err, res) => {
        if (err) {
          done(err);
        }
        res.status.should.be.equal(200);
        let id = res.body[0]._id;

        // Upvote the Post, then downvote it to balance
        chai.request(url)
	    .put('/posts/' + id + "/upvote")
        .send()
	    .end(function (err, res){
	    	if (err) {
	          done(err);
	        }
	        res.status.should.be.equal(200);

			chai.request(url)
		    .put('/posts/' + id + "/downvote")
	        .send()
		    .end(function (err, res){
		    	if (err) {
		          done(err);
		        }
		        res.status.should.be.equal(200);	        
	    	});

        	done();                           // Our test completed successfully.
      	});
	  
  	  });
	});
});