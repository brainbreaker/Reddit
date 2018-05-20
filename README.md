# Reddit
A simple reddit clone on Android backed by dummy Node.js API

This project has two parts - Android app(Reddit-Android) and a dummy API(Reddit-Server) written in Node.js backed by MongoDB. 

**Reddit-Android** app consumes the API to render a list of Top 20 posts in sorted order. It supports some basic features like upvoting/downvoting a post, creating a new post and refresh.

**Reddit-Server** API is a basic implementation with which you can perform basic CRUD(CREATE, READ, UPDATE and DELETE) operations on Posts. It is backed by a predefined MongoDB database hosted on [Atlas](https://docs.atlas.mongodb.com/). This API also lives on [Heroku](https://www.heroku.com) - http://pacific-caverns-24056.herokuapp.com/ 

See [API Documentation and Usage](#api-documentation-and-usage) below on how to use it. Source code is has **detailed inline comments** to make it more understandable wherever needed.

# Reddit-Android

## Useful Directory Structure of Reddit-Android
```
Reddit-Android/
    |- app/                  - Encapsulates the core of the application
        |- src/              - Main source code of the application 
            |- androidTest/  - Contains instrumentation tests for the app 
            |- main/         - Java source code lives here
            |- test/         - Unit tests live in this folder
        |- build.gradle/     - App level build.gradle containing dependencies
    |- gradle/wrapper        - Gradle wrapper jar and properties file 
    |- .gitignore            - Android level gitignore
    |- build.gradle          - Top-level build.gradle
```

## Build Setup and Information

1. Clone the project and open the Reddit-Android/ project in Android Studio. This project has been coded in Android Studio 3.0.1

2. Android Studio will automatically take care of your build. Just to note, this project -
    - Targets SDK version 27
    - Uses Android Gradle Plugin Version 3.1.1
    - Uses Retrofit and GSON to interact with API
    - Uses Picasso to load the images
    - Recycler View and Card View from Android support library
    - JUnit and Espresso for writing unit and instrumentation tests

3. After build succeeds, connect your Android Device and  go to `Run -> Run app` to install the application on your device. An **APK** can also be found [here](https://drive.google.com/file/d/1YyHCMFxP5ZFDLTmMS_eY5IE4Gpq4FxcE/view?usp=sharing).

4. To run tests, from Android Studio from `Select Run/Debug Configuration` dropdown, choose `MainActivityTest` or `UpvoteDownvoteTest` and click 'Run'. 
    You can also navigate to `androidTest/` or `test/` directory under `app/src/`, right click and select Run on respective test. `UpvoteDownvoteTest` is a unit test which can be run without any device, however `MainActivityTest` being an instrumentation test would need a device or emulator.

5. This app has been tested on Motorola G4 Plus physical device running Android 7.0 and Nexus 5X emulator running on Android 8.0.

## Brief Overview of Java Source Code

`app/src/` has been divided into three major packages -

1. `androidTest/` - Contains one instrumentation test `MainActivityTest.java` which tests MainActivity with respect to RecyclerView, whether it is being displayed or not, whether it has items or not, whether upvoting/downvoting posts is successful etc.

2. `test/` - Contains one unit test which tests whether requests on upvote/downvote endpoints are successful and increasing/decreasing the count of votes. 

3. `src/` - Contains different packages like - 
    - `Adapter/` - Contains RecyclerViewAdapter class which populates the content in RecyclerView
    - `Models/` - Contains the model classes for a post and simple JSON response
    - `Retrofit/` - Contains the `RetrofitAPIClient` and `RedditAPIInterface` defining methods to interact with API Endpoints.
    - `MainActivity.java` - Home page of the app which displays the list of posts in recycler view
    - `NewPostActivity.java` - Contains the form to create a new post 

## Video

The video of application can be found here - https://drive.google.com/open?id=1D0yR461xRJxB0cXn5nY3Il92L3FuAO_x 



# Reddit-Server

## Directory Structure
```
Reddit-Server/
    |- config/
        |- db.js             - Defines config and connects to MongoDB Atlas
    |- controllers/            
        |- postController.js - Defines methods handling calls at API endpoints 
    |- init/                 
        |- initializeDB.js   - Script to populate local MongoDB with dummy data
    |- models/               
        |- post.js           - Model class for our posts
    |- test/
        |- allPosts.js       - Tests the GET `/posts` endpoint
        |- createPost.js     - Tests the POST and DELETE at `/posts`       
        |- votePosts.js      - Tests the upvote and downvote endpoints
    |- index.js              - Entry point of the app, defines routes
    |- package.json          - Standard Node.js project file listing packages
  
```

## Project Setup

1. Setup Node.js Development Setup environment according to your OS by following the steps [here](http://www.tutorialsteacher.com/nodejs/setup-nodejs-development-environment).
2. Clone the repository and `cd` into `Reddit-Server/`
3. Run `npm install` to install all the packages specified in `package.json` locally in your system. 
4. Fire up terminal and type `node index.js` to run the server. It'll first populate the dummy data in database. You can also specify the `PORT` beforehand by `export PORT = YOUR_PORT_NUMBER` to run the server at your preferred `PORT`.
6. To run the tests, fire up a new terminal tab after running the server and type `npm test`. It'll run all three tests from `test/` directory.

## API Documentation and Usage

Complete Documentation of the API is available here - https://documenter.getpostman.com/view/689808/reddit-server/RW87opLS

API is deployed on Heroku and is accessible at - https://pacific-caverns-24056.herokuapp.com/

You can take a look at the list of all the posts in your browser through this link - https://pacific-caverns-24056.herokuapp.com/posts

## TODO
* [ ] Make a more robust API by handling Authentication as well
* [ ] Improve the code by proper exception handling
* [ ] Add repository badges like TravisCI, Better Code, Codacy etc.
* [ ] Write more robust tests for API as well as Android app 
* [ ] User and roles management
* [ ] Session management using JWT tokens
* [ ] Add data persistence in Android app
* [ ] Improve Design of Android app




