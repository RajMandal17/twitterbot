package com.tweetscan.raj;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twitter")
public class TwitterController {

    private final com.tweetscan.raj.TwitterService twitterService;

    public TwitterController(com.tweetscan.raj.TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/tweets/{userId}")
    public List<com.tweetscan.raj.Tweet> getTweets(@PathVariable String userId) {
        return twitterService.getTweets(userId);
    }
}
