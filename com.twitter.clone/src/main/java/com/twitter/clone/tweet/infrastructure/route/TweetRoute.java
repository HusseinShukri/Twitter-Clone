package com.twitter.clone.tweet.infrastructure.route;

import com.google.inject.Inject;
import com.twitter.clone.infrastructure.route.BaseRoutes;
import com.twitter.clone.tweet.api.controller.TweetController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class TweetRoute extends BaseRoutes {
    private final TweetController tweetController;

    @Override
    protected void initializeControllerInstances() {
        super.controllerInstancesMap.put(TweetController.class, tweetController);
    }
}