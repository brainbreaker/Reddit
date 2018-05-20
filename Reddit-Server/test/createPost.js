const chai = require('chai')
const chaiHttp = require('chai-http')
const should = chai.should()
const Post = require('../models/post');
var url = "http://localhost:" + (process.env.PORT || 8000);


describe('Create a new post', () => {
  it('Should create a new sample reddit post at POST /posts', (done) => {
    
	  chai.request(url)
	    .post('/posts')
	    .set('content-type', 'application/x-www-form-urlencoded')
        .send({ title: "Reddit post title", 
        	    image_url: "https://www.example.com", 
        	    content: "Reddit Post description",
                author: "u/brainbreaker",
        		subreddit: "r/example",
        		votes: "100"})
	    .end(function (err, res){
	    	if (err) {
	          done(err);
	        }

	        res.status.should.be.equal(200);

			// Now delete the newly created post	        
	        chai.request(url)
	        	.delete('/posts/' + res.body._id)
	        	.end(function(err, res){
		          res.should.have.status(200);
		          done();
			    });
	    });
  })
})