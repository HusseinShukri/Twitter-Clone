package com.twitter.clone.tweet.api.controller;

import com.google.inject.Inject;
import com.twitter.clone.authentication.api.servcie.IUserService;
import com.twitter.clone.infrastructure.annotation.route.Http;
import com.twitter.clone.infrastructure.annotation.route.RouteController;
import com.twitter.clone.infrastructure.commen.exceptions.UnauthorizedException;
import com.twitter.clone.tweet.api.mapper.TweetMapper;
import com.twitter.clone.tweet.domain.service.TweetService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RouteController("tweet")
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class TweetController {

    private final IUserService userService;
    private final TweetService tweetService;
    private final TweetMapper mapper;

    @Http.Get("{tweetId}")
    public void getTweet(Context context) {
        var tweetId = Integer.parseInt(context.pathParam("tweetId"));
        var projectDto = tweetService.get(tweetId);
        if (projectDto == null) {
            context.status(HttpStatus.NOT_FOUND);
        } else {
            context.json(projectDto);
        }
    }

    @Http.Post("new-tweet")
    public void newTweet(Context context) throws UnauthorizedException {
        String userId = context.attribute("Id");

        if (userId == null) {
            throw new UnauthorizedException("User ID not provided.");
        }

        var user = userService.findUser(Integer.parseInt(userId));

        var newTweet = mapper.contextToNewTweetDto(context, user.getId());
        tweetService.insert(newTweet);
        //TODO separate the post response from page render and handel this case in better way
        context.status(HttpStatus.SEE_OTHER);
        context.header("hx-redirect", "/twitter-clone/newsfeed/index");
    }

    @Http.Post("retweet/{tweetId}")
    public void retweet(Context context) throws UnauthorizedException {

        var newRetweet = mapper.contextToNewRetweetDto(context);

        var user = userService.findUser(newRetweet.userId());

        tweetService.insertRetweet(newRetweet);
        //TODO we need to return a proper response
        context.status(HttpStatus.SEE_OTHER);
        context.header("hx-redirect", "/twitter-clone/newsfeed/index");

    }
}
