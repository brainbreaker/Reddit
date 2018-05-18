const mongoose = require('mongoose')

// MongoDB database URI
const uri = "mongodb://carousell:carousell@greddit-shard-00-00-njkug.mongodb.net:27017,greddit-shard-00-01-njkug.mongodb.net:27017,greddit-shard-00-02-njkug.mongodb.net:27017/test?ssl=true&replicaSet=greddit-shard-0&authSource=admin&retryWrites=false";
// Mongoose Options
const options = {
  reconnectTries: Number.MAX_VALUE,
  poolSize: 10
};


// We're going to use Javascript Promise
mongoose.Promise = global.Promise
mongoose.connect(uri, options).then(
  () => {
    console.log("We're now connected to the database!");
  },
  err => {
    console.log("Uh-oh, There was an error connecting to the database - ", err);
  }
);

mongoose.set('debug', true)