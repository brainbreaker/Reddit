var fs = require('fs');
var inputFile = './data.txt';
var mongoose = require('mongoose');
var Post = require('../models/Post.js');

var postsBag = [
	{ title: "Something about Spock", subreddit: "r/todayilearned", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Leonard_Nimoy_as_Spock_1967.jpg/440px-Leonard_Nimoy_as_Spock_1967.jpg", content: "https://www.reddit.com/r/todayilearned/comments/8kdbmq/til_that_while_developing_star_trek_spock_was/", votes: 20, author: "u/Zurzwurgle"},
	{ title: "Kids may be paying more attention when you say vegetables are good for them", subreddit: "r/science", image_url: "https://news-media.stanford.edu/wp-content/uploads/2018/05/11143552/FoodRules.jpg", content: "https://www.reddit.com/r/science/comments/8kd9wd/kids_may_be_paying_more_attention_than_you_think/", votes: 112, author: "u/mvea"},
	{ title: "The world agreed to ban this ozone killer years ago", subreddit: "r/science", image_url: "https://www.popsci.com/sites/popsci.com/files/styles/655_1x_/public/images/2018/05/depositphotos_4490532_original.jpg?itok=P90Nf1Sn&fc=50,50", content: "https://www.reddit.com/r/science/comments/8k8ima/the_world_agreed_to_ban_this_ozone_killer_years/", votes: 23, author: "u/FillsYourNiche"},
	{ title: "Study finds a surge in young Americans using marijuana", subreddit: "r/health", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Cannabis_Plant.jpg/440px-Cannabis_Plant.jpg", content: "https://www.theguardian.com/science/2018/may/17/surge-in-young-americans-using-marijuana-as-first-drug", votes: 35, author: "u/drewiepoodle"},
	{ title: "Bitcoin uses as much energy as Ireland", subreddit: "r/science", image_url: "https://static.euronews.com/articles/3156734/1280x720_3156734.jpg", content: "Study claims Bitcoin uses as much energy as Ireland, but experts don't agree. The rise in popularity of Bitcoin and other cryptocurrencies has been met with growing concern about the energy usage required by the thousands of computing systems that power these virtual currencies.", votes: 12, author: "u/wedt"},
	{ title: "Cable has lost 3.4 million TV customers since 2012", subreddit: "r/television", image_url: "", content: "http://www.dslreports.com/shownews/Cable-Has-Lost-34-Million-TV-Customers-Since-2012-141853", votes: 35, author: "u/fdg"},
	{ title: "[Spoilers] Greatest achievement in game of thrones.", subreddit: "r/gameofthrones", image_url: "https://i.imgur.com/JcR5K6F.jpg", content: "https://i.imgur.com/JcR5K6F.jpg", votes: 45, author: "u/mert"},
	{ title: "Reports of shots fired at Santa Fe High School", subreddit: "r/news", image_url: "https://santafe.edmondschools.net/wp-content/uploads/sites/8/2015/09/Testing.jpg", content: "https://www.reddit.com/r/news/comments/8kcysk/reports_of_shots_fired_at_santa_fe_high_school/", votes: 220, author: "u/BungoPlease"},
	{ title: "Rugs the island natives are carrying in ‘Moana’", subreddit: "r/moviedetails", image_url: "https://i.redditmedia.com/os3AEfBADhUJuKVFp0sLifAzXJQmQCZMhcUFz9z0A5E.jpg?s=4852c8ab688b8d765a7763d031e94fc8", content: "https://www.reddit.com/r/MovieDetails/comments/8kcuvx/one_of_the_rugs_the_island_natives_are_carrying/", votes: 30, author: "u/Hdalby33"},
	{ title: "Buying 1000 likes for 5 dollars", subreddit: "r/standupshots", image_url: "https://i.redditmedia.com/yXxF3jdzFto4RQyDaALdmUq3dtLMDWCdGYIK0b5MkFE.jpg?s=c4b6bfcf8eff031cd8cb34cfe9cbccfb", content: "https://www.reddit.com/r/standupshots/comments/8kchez/buying_1000_likes_for_5_dollars/", votes: 57, author: "u/richsarvate"},
	{ title: "Dolly Parton Donates $1 Million to Children's Hospital in Honor of Niece", subreddit: "r/UpliftingNews", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Dolly_Parton_2011.jpg/440px-Dolly_Parton_2011.jpg", content: "https://www.reddit.com/r/UpliftingNews/comments/8kck0s/dolly_parton_donates_1_million_to_childrens/", votes: 200, author: "u/Sariel007"},
	{ title: "The Happytime Murders | Official Restricted Trailer | Melissa McCarthy", subreddit: "r/movies", image_url: "https://blogs-images.forbes.com/scottmendelson/files/2018/05/maxresdefault-2-1-1200x675.jpg", content: "https://www.reddit.com/r/movies/comments/8kdenu/the_happytime_murders_official_restricted_trailer/", votes: 5, author: "u/Sisiwakanamaru"},
	{ title: "[Spoilers] Greatest achievement in game of thrones.", subreddit: "r/gameofthrones", image_url: "https://i.imgur.com/JcR5K6F.jpg", content: "https://i.imgur.com/JcR5K6F.jpg", votes: 44, author: "u/mert"},
	{ title: "Reports of shots fired at Santa Fe High School", subreddit: "r/news", image_url: "https://santafe.edmondschools.net/wp-content/uploads/sites/8/2015/09/Testing.jpg", content: "https://www.reddit.com/r/news/comments/8kcysk/reports_of_shots_fired_at_santa_fe_high_school/", votes: 32, author: "u/BungoPlease"},
	{ title: "Bitcoin uses as much energy as Ireland", subreddit: "r/science", image_url: "https://static.euronews.com/articles/3156734/1280x720_3156734.jpg", content: "Study claims Bitcoin uses as much energy as Ireland, but experts don't agree. The rise in popularity of Bitcoin and other cryptocurrencies has been met with growing concern about the energy usage required by the thousands of computing systems that power these virtual currencies.", votes: 93, author: "u/wedt"},
	{ title: "Something about Spock", subreddit: "r/todayilearned", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Leonard_Nimoy_as_Spock_1967.jpg/440px-Leonard_Nimoy_as_Spock_1967.jpg", content: "https://www.reddit.com/r/todayilearned/comments/8kdbmq/til_that_while_developing_star_trek_spock_was/", votes: 235, author: "u/Zurzwurgle"},
	{ title: "Kids may be paying more attention when you say vegetables are good for them", subreddit: "r/science", image_url: "https://news-media.stanford.edu/wp-content/uploads/2018/05/11143552/FoodRules.jpg", content: "https://www.reddit.com/r/science/comments/8kd9wd/kids_may_be_paying_more_attention_than_you_think/", votes: 12, author: "u/mvea"},
	{ title: "Cable has lost 3.4 million TV customers since 2012", subreddit: "r/television", image_url: "", content: "http://www.dslreports.com/shownews/Cable-Has-Lost-34-Million-TV-Customers-Since-2012-141853", votes: 87, author: "u/fdg"},
	{ title: "Cable has lost 3.4 million TV customers since 2012", subreddit: "r/television", image_url: "", content: "http://www.dslreports.com/shownews/Cable-Has-Lost-34-Million-TV-Customers-Since-2012-141853", votes: 52, author: "u/fdg"},
	{ title: "[Spoilers] Greatest achievement in game of thrones.", subreddit: "r/gameofthrones", image_url: "https://i.imgur.com/JcR5K6F.jpg", content: "https://i.imgur.com/JcR5K6F.jpg", votes: 76, author: "u/mert"},
	{ title: "Reports of shots fired at Santa Fe High School", subreddit: "r/news", image_url: "https://santafe.edmondschools.net/wp-content/uploads/sites/8/2015/09/Testing.jpg", content: "https://www.reddit.com/r/news/comments/8kcysk/reports_of_shots_fired_at_santa_fe_high_school/", votes: 83, author: "u/BungoPlease"},
	{ title: "Rugs the island natives are carrying in ‘Moana’", subreddit: "r/moviedetails", image_url: "https://i.redditmedia.com/os3AEfBADhUJuKVFp0sLifAzXJQmQCZMhcUFz9z0A5E.jpg?s=4852c8ab688b8d765a7763d031e94fc8", content: "https://www.reddit.com/r/MovieDetails/comments/8kcuvx/one_of_the_rugs_the_island_natives_are_carrying/", votes: 7, author: "u/Hdalby33"},
	{ title: "Buying 1000 likes for 5 dollars", subreddit: "r/standupshots", image_url: "https://i.redditmedia.com/yXxF3jdzFto4RQyDaALdmUq3dtLMDWCdGYIK0b5MkFE.jpg?s=c4b6bfcf8eff031cd8cb34cfe9cbccfb", content: "https://www.reddit.com/r/standupshots/comments/8kchez/buying_1000_likes_for_5_dollars/", votes: 2, author: "u/richsarvate"},
	{ title: "Dolly Parton Donates $1 Million to Children's Hospital in Honor of Niece", subreddit: "r/UpliftingNews", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Dolly_Parton_2011.jpg/440px-Dolly_Parton_2011.jpg", content: "https://www.reddit.com/r/UpliftingNews/comments/8kck0s/dolly_parton_donates_1_million_to_childrens/", votes: 78, author: "u/Sariel007"},
	{ title: "The Happytime Murders | Official Restricted Trailer | Melissa McCarthy", subreddit: "r/movies", image_url: "https://blogs-images.forbes.com/scottmendelson/files/2018/05/maxresdefault-2-1-1200x675.jpg", content: "https://www.reddit.com/r/movies/comments/8kdenu/the_happytime_murders_official_restricted_trailer/", votes: 56, author: "u/Sisiwakanamaru"},
	{ title: "[Spoilers] Greatest achievement in game of thrones.", subreddit: "r/gameofthrones", image_url: "https://i.imgur.com/JcR5K6F.jpg", content: "https://i.imgur.com/JcR5K6F.jpg", votes: 22, author: "u/mert"},
	{ title: "The world agreed to ban this ozone killer years ago", subreddit: "r/science", image_url: "https://www.popsci.com/sites/popsci.com/files/styles/655_1x_/public/images/2018/05/depositphotos_4490532_original.jpg?itok=P90Nf1Sn&fc=50,50", content: "https://www.reddit.com/r/science/comments/8k8ima/the_world_agreed_to_ban_this_ozone_killer_years/", votes: 93, author: "u/FillsYourNiche"},
	{ title: "Study finds a surge in young Americans using marijuana", subreddit: "r/health", image_url: "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Cannabis_Plant.jpg/440px-Cannabis_Plant.jpg", content: "https://www.theguardian.com/science/2018/may/17/surge-in-young-americans-using-marijuana-as-first-drug", votes: 89, author: "u/drewiepoodle"},
	{ title: "Bitcoin uses as much energy as Ireland", subreddit: "r/science", image_url: "https://static.euronews.com/articles/3156734/1280x720_3156734.jpg", content: "Study claims Bitcoin uses as much energy as Ireland, but experts don't agree. The rise in popularity of Bitcoin and other cryptocurrencies has been met with growing concern about the energy usage required by the thousands of computing systems that power these virtual currencies.", votes: 24, author: "u/wedt"},
	{ title: "Cable has lost 3.4 million TV customers since 2012", subreddit: "r/television", image_url: "", content: "http://www.dslreports.com/shownews/Cable-Has-Lost-34-Million-TV-Customers-Since-2012-141853", votes: 41, author: "u/fdg"},
	{ title: "[Spoilers] Greatest achievement in game of thrones.", subreddit: "r/gameofthrones", image_url: "https://i.imgur.com/JcR5K6F.jpg", content: "https://i.imgur.com/JcR5K6F.jpg", votes: 2, author: "u/mert"},
]

exports.initializeDatabase = function(callback) {
    
  Post.remove({}, function(err){
     Post.collection.insert(postsBag, onInsert);
     
     function onInsert(err, docs) {
	    if (err) {
	        console.log("Some error occurred - " + err);
	    } else {
	        console.info('Posts were successfully stored');
	    }
	 }
  });

};
