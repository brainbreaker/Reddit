const chai = require('chai')
const chaiHttp = require('chai-http')
const should = chai.should()

chai.use(chaiHttp)
var url = "http://localhost:" + (process.env.PORT || 8000);

describe('All posts path', () => {                  // Describes what you are testing
  it('Should list all reddit posts', (done) => {           // Describes what should happen
    // In this case we test that the home page loads
    chai.request(url)
      .get('/posts')
      .end((err, res) => {
        if (err) {
          done(err)
        }
        res.status.should.be.equal(200)
        done()                           // Our test completed successfully.
      })
  })
})