const mongoose = require('mongoose')
const Schema = mongoose.Schema

const PostSchema = new Schema({
  createdAt:  	  { type: Date },
  updatedAt:	  { type: Date },
  title:    	  { type: String, required: true },
  subreddit:      { type: String, required: true }, 
  image_url:      { type: String, required: true },
  author:         { type: String, required: true },
  content:        { type: String, required: true },
  votes:          { type: Number, required: true }
})

PostSchema.pre('save', (next) => {
  // Set createdAt AND updatedAt values
  const now = new Date()
  this.updatedAt = now
  if (!this.createdAt) {
    this.createdAt = now
  }
  next()
})

module.exports = mongoose.model('Post', PostSchema)